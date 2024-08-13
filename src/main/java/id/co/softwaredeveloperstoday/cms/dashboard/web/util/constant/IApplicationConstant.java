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
            String MEMBER_DEFAULT = "/member-default";
            String RECOMMENDED_USERNAME = "/recommended-username";
        }

        interface User {
            String USER_V1 = API + "/v1/user";
            String CHANGE_PASSWORD = "/change-password";
            String CURRENT_ROLE = "/current-role";
            String CHANGE_PASSWORD_ALLOWED_USER = "/allowed-user-to-change-password";
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
            String ERROR_OLD_PASSWORD_NOT_MATCH = "Old password is invalid";
            String ERROR_NEW_PASSWORD_NOT_MATCH = "New password is not match";
            String ERROR_MESSAGE_USER_FOUND = "User not found according to username";

            String ERROR_MESSAGE_DATA_NOT_FOUND = "No Data found";

            String ERROR_MESSAGE_GENERAL_REQUIRED_FIELDS = " Required Parameter(s) should Be Filled: There is/are field(s)/parameter(s) that is/are empty/blank. Please insert Required Field(s) ";
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
            String FIND_BY_NAME = "name";

            String PAGE = "page";
            String SIZE = "size";
        }

        interface CommonRestPath {
            String NAME = "name";

            String FIND_BY_NAME = "find-by-name";
        }
    }

}
