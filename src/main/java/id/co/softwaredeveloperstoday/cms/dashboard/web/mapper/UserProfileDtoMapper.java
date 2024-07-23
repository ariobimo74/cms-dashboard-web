package id.co.softwaredeveloperstoday.cms.dashboard.web.mapper;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.AddUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.User;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserProfileDtoMapper {

    public abstract UserProfile convertUserProfile(AddUserProfileDto userProfileDto);

    public abstract User convertUser(AddUserProfileDto addUserProfileDto);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "password", source = "user.password")
    public abstract AddUserProfileDto convertAddUserProfileDto(UserProfile userProfile);

}
