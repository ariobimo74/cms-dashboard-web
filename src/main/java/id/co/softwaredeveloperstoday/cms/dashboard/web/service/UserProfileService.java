package id.co.softwaredeveloperstoday.cms.dashboard.web.service;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.AddUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.UserProfileDetailDto;

public interface UserProfileService {

    AddUserProfileDto addUserProfile(AddUserProfileDto userProfileDto);

    UserProfileDetailDto findUserById(Long userProfileId);

}
