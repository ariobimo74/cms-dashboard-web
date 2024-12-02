package id.co.softwaredeveloperstoday.cms.dashboard.web.service;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.AddUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.EditUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.UserProfileDetailDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.UserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.EUserSortBy;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.Date;

public interface UserProfileService {

    AddUserProfileDto addUserProfile(AddUserProfileDto userProfileDto);

    UserProfileDetailDto findUserById(Long userProfileId);

    UserProfileDetailDto updateUserProfile(Authentication authentication, EditUserProfileDto editUserProfileDto);

    Page<UserProfileDto> getAllPaging(String searchName, String searchUsername, String searchDate, Integer page, Integer size, EUserSortBy sortBy, boolean isAscendingSort);

}
