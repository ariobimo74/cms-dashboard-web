package id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EDataTableSortBy {
    NAME("name"), ID_CARD_NUMBER("idCardNumber"), DATE_OF_BIRTH("dateOfBirth"), PLACE_OF_BIRTH("placeOfBirth"), GENDER("gender"), USERNAME("user.username"), ROLE("user.userRoles.role.roleName"), MOBILE_PHONE_NUMBER("mobilePhoneNumber"), EMAIL("email"), MEMBER_LEVEL("memberLevel");

    private final String name;
}
