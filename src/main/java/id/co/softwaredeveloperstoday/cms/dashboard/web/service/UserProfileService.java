package id.co.softwaredeveloperstoday.cms.dashboard.web.service;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.*;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.EDataTableSortBy;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.EUserSortBy;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.Date;

public interface UserProfileService {

    AddUserProfileDto addUserProfile(AddUserProfileDto userProfileDto);

    UserProfileDetailDto findUserById(Long userProfileId);

    UserProfileDetailDto updateUserProfile(Authentication authentication, EditUserProfileDto editUserProfileDto);

    Page<UserProfileDetailDto> getAllPaging(String searchName, String searchUsername, String searchDate, Integer page, Integer size, EUserSortBy sortBy, boolean isAscendingSort);

    ResponseDataTableDto<UserProfileDetailDto> getAllPagingDataTable(int draw, String search, Integer page, Integer size, EDataTableSortBy dataTableSortBy, boolean isAscendingSort);

}
