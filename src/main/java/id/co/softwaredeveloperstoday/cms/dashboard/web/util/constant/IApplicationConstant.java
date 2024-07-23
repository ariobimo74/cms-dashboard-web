package id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant;

public interface IApplicationConstant {

    interface RestVersion {
        String API = "/api";

        interface Menu {
            String MENU_V1 = API + "/v1/menu";
            String MENU_V2 = API + "/v2/menu";
        }

        interface Register {
            String REGISTER_V1 = API + "/v1/register";
        }

        interface Role {
            String ROLE_V1 = API + "/v1/role";
        }

        interface CommonValue {
            String COMMON_VALUE = API + "/v1/value";
            String GENDER = "/gender";
            String MEMBER = "/member";
            String RECOMMENDED_USERNAME = "/recommended-username";
        }

        interface User {
            String USER_V1 = "/v1/user";
            String CHANGE_PASSWORD = "/change-password";
        }
    }

    interface SchemaName {
        String MASTER_AUTHENTICATION = "master_authentication";
        String MASTER_ADDRESS = "master_address";
    }

    interface CommonMessage {
        interface ErrorMessage {
            String ERROR_MESSAGE_USER = "Invalid Username and Password";
            String ERROR_MESSAGE_USER_NOT_ALLOWED = "User is not allowed to change other users' password";
            String ERROR_OLD_PASSWORD_NOT_MATCH = "Old password not match";
        }
    }

    interface CommonValue {
        interface DefaultEntityWhereClause {
            String ENTITY_WHERE_CLAUSE = " is_delete = false ";
        }

        interface RestResponseValue {
            String SUCCESS = "SUCCESS";
            String FAILED = "FAILED";
        }

        interface CommonRestParam {
            String FIND_BY_NAME = "/{name}";
        }
    }

}
