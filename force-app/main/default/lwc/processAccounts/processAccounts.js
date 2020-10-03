import { LightningElement, api } from "lwc";
import { ShowToastEvent } from "lightning/platformShowToastEvent";
import process from "@salesforce/apex/ProcessAccountsCtrl.process";

export default class UpdateAccounts extends LightningElement {
    @api recordId;
    @api recordIds;


    columns = [
        {
            label: "Name", 
            fieldName: "link", 
            type: "url", 
            typeAttributes: { 
                label: { 
                    fieldName: "name" 
                },
                target: "_blank"
            }
        },
        {label: "Type", fieldName: "type"},
        {label: "Message", fieldName: "message"}
    ];

    processedMessage;

    showProcessResult = false;
    isLoading = true;

    connectedCallback() {
        const accountIds = this.recordId ? [this.recordId] : this.recordIds;

        if(!accountIds || !accountIds.length) {
            this.showToast("warning", "", "Please select atleast one Account");
            this.close();            
        }
        else {
            this.process(accountIds);
        }
    }


    get accounts() {
        const accounts = JSON.parse(this.response.message);    
        return accounts.map((account) => {
            return {
                ...account,
                link: `/${account.id}`
            };
        });
    }


    get message() {
        return this.response.status === "success" ? "yay!! no problems detected" : "oh snap! few problems detected";
    }


    get responseSentementClass() {
        return this.response.status === "success" ? "slds-text-color_success" : "slds-text-color_error";
    }


    get isSingleRecord() {
        return !!this.recordId;
    }


    async process(accountIds) {
        this.isLoading = true;

        try {
            this.response = await process({accountIds});
            this.showProcessResult = true;
        } catch (error) {
            this.showToast("error", error.body.exceptionType, error.body.message);
            this.close();
        }

        this.isLoading = false;
    }


    showToast(variant, title, message) {
        if(this.recordId) {
            const evt = new ShowToastEvent({ variant, title, message });
            this.dispatchEvent(evt);
        }
        else {
            const closeQA = new CustomEvent("showToast", {  
                detail: { type: variant, title, message },
                bubbles: true,
                composed: true
            });
            this.dispatchEvent(closeQA);
        }
    }


    close() {
        const closeQA = new CustomEvent("close", {   
            bubbles: true,
            composed: true
        });
        this.dispatchEvent(closeQA);
    }
}