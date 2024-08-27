package id.co.softwaredeveloperstoday.cms.dashboard.web.service;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.AddUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.EditUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.UserProfileDetailDto;
import org.springframework.security.core.Authentication;

public interface UserProfileService {

    AddUserProfileDto addUserProfile(AddUserProfileDto userProfileDto);

    UserProfileDetailDto findUserById(Long userProfileId);

    UserProfileDetailDto updateUserProfile(Authentication authentication, EditUserProfileDto editUserProfileDto);

}
