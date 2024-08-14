package id.co.softwaredeveloperstoday.cms.dashboard.web.mapper;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.AddUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.UserProfileDetailDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.User;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class UserProfileDtoMapper {

    public abstract UserProfile convertUserProfile(AddUserProfileDto userProfileDto);

    public abstract User convertUser(AddUserProfileDto addUserProfileDto);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "password", source = "user.password")
    public abstract AddUserProfileDto convertAddUserProfileDto(UserProfile userProfile);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "firstName", expression = "java(splitName(userProfile.getName(), 0))")
    @Mapping(target = "lastName", expression = "java(splitName(userProfile.getName(), 1))")
    public abstract UserProfileDetailDto convertUserProfileDetailDto(UserProfile userProfile);

    String splitName(String fullName, int index) {
        if (Objects.isNull(fullName)) return "";

        try {
            String[] names = fullName.split(" ");

            if (index == 0) {
                return names[index];
            } else {
                if (names.length > 2) {
                    StringBuilder resultName = new StringBuilder();
                    for (int i = 1; i < names.length; i++) {
                        resultName.append(names[i]).append(" ");
                    }

                    return resultName.toString();
                } else return names[index];
            }
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}
