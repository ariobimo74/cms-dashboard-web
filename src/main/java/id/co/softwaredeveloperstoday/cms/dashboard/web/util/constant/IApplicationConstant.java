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

            String ADDITIONAL_PAGINATION_PARAMETERS = "/additional-pagination-parameters";
        }

        interface User {
            String USER_V1 = API + "/v1/user";

            String VIEW_USER_PROFILES = "/profiles";
            String VIEW_USER_PROFILES_DATA_TABLE = "/profiles-data-table";
            String VIEW_USER_PROFILE = "/profile/{id}";
            String EDIT_USER_PROFILE = "/edit/{id}";

            String CHANGE_PASSWORD = "/change-password";
            String CURRENT_ROLE = "/current-role";
            String CHANGE_PASSWORD_ALLOWED_USER = "/allowed-user-to-change-password";
            String CREATE_EDIT_ACTION_ALLOWED_USER = "/allowed-user-to-create-edit-user";
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

            String ERROR_MESSAGE_USER_FOUND_USERNAME = "User not found according to username";
            String ERROR_MESSAGE_USER_NOT_FOUND = "User not found";

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
            String FIND_BY_USER_NAME = "username";
            String FIND_BY_DATE_OF_BIRTH = "date-of-birth";

            String PAGE = "page";
            String SIZE = "size";
        }

        interface CommonRestPath {
            String NAME = "name";
            String ID = "id";

            String FIND_BY_NAME = "find-by-name";
        }

        interface TimeZone {
            String DEFAULT = "Asia/Jakarta";
        }

        interface Pagination {
            Integer DEFAULT_PAGE_NUMBER = 1;
            Integer DEFAULT_PAGE_SIZE = 10;

            String DEFAULT_SORT_BY = "id";

            String SORT_TYPE_PARAMETER = "sort-type";
            String SORT_BY_PARAMETER = "sort-by";
        }
    }

}
