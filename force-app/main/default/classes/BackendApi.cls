public with sharing class BackendApi extends ffhttp_Client.AbstractClient {

    // CONSTRUCTOR

    public BackendApi() {
        setCredentials( new ffhttp_Client.NamedCredentials('Backend') );
    }


    // PUBLIC

    public Response sendAccounts(List<Account> accounts, Boolean init) {
        return (Response) new HttpClientRequest(this, syncEndpoint(),
                                                        ffhttp_Client.REQUEST_METHOD_POST, new Request(accounts), 
                                                        new ffhttp_JsonDeserializer(Response.class))
                                                        .param('init', init)
                                                        .execute();
    }


    public Response process() {
        Response result = (Response) new HttpClientRequest(this, processEndpoint(),
                                                            ffhttp_Client.REQUEST_METHOD_POST, new Request(),
                                                            new ffhttp_JsonDeserializer(Response.class))
                                                            .execute();
        new SaveSyncResponse(result.accounts).execute();

        return result;
    }


    // PRIVATE

    private String syncEndpoint() {
        return '/batch';
    }


    private String processEndpoint() {
        return '/process';
    }


    // INNER

    private class HttpClientRequest extends ffhttp_Client.AbstractClientRequest {
        public HttpClientRequest(AbstractClient abstractClient, 
                                 String endpoint,
                                 String requestMethod,
                                 ffhttp_IHttpContent httpContent,
                                 ffhttp_IDeserialize responseDeserializer) {
            super(abstractClient, endpoint, requestMethod, httpContent, responseDeserializer);
        }
        
        
        private HttpClientRequest param(String key, Object param) {
            super.addParam(key, param);
            return this;
        }
    }


    public class Request extends ffhttp_AbstractJsonSerializable {
        public String orgId = UserInfo.getOrganizationId();
        public String userId = UserInfo.getUserId();
        public List<Account> accounts;

        @SuppressWarnings('PMD.EmptyStatementBlock')
        public Request() {
            // Note: Nothing to do
        }


        public Request(List<Account> accounts) {
            this.accounts = accounts;
        }
    }


    public class Response extends ffhttp_AbstractJsonSerializable {
        @AuraEnabled
        public String status;
        @AuraEnabled
        public String message;
        @AuraEnabled
        public List<AccountResponse> accounts;
        
        @SuppressWarnings('PMD.EmptyStatementBlock')
        public Response() {
            // Note: Nothing to do
        }
        
        public Response(String status) {
            this.status = status;
        }
        
        public Response(String status, List<AccountResponse> accounts) {
            this.status = status;
            this.accounts = accounts;
        }

        public Boolean isSuccess() {
            return (status == 'success');
        }
    }


    public class AccountResponse {
        @AuraEnabled
        public Id id;
        @AuraEnabled
        public String name;
        @AuraEnabled
        public String type;
        @AuraEnabled
        public String message;

        @SuppressWarnings('PMD.EmptyStatementBlock')
        public AccountResponse() {
            // Note: Nothing to do
        }

        public AccountResponse(Account account, String message) {
            this.id = account.Id;
            this.message = message;
            this.name = account.Name;
            this.type = account.Type;
        }
    }
}