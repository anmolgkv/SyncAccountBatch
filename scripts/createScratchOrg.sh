#!/bin/bash
source `dirname $0`/config.sh

execute() {
  $@ || exit
}

echo "set default devhub org"
execute sfdx force:config:set defaultdevhubusername=$DEV_HUB_ALIAS

echo "deleting old scratch org"
sfdx force:org:delete -p -u $SCRATCH_ORG_ALIAS

echo "Creating scratch ORG"
execute sfdx force:org:create -a $SCRATCH_ORG_ALIAS -s -f ./config/project-scratch-def.json -d 30

echo "Make sure Org user is english"
sfdx force:data:record:update -s User -w "Name='User User'" -v "Languagelocalekey=en_US"

echo "Pushing changes to scratch org"
execute sfdx force:source:push

echo "Running apex tests"
execute sfdx force:apex:test:run -l RunLocalTests -w 30


echo "setup sample data"
sfdx force:apex:execute -f ./scripts/prepareData.apex