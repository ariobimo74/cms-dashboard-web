package id.co.softwaredeveloperstoday.cms.dashboard.web.service.impl;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dao.RoleDao;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dao.UserProfileDao;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.AddUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.EditUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.UserProfileDetailDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.UserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.mapper.RoleMapper;
import id.co.softwaredeveloperstoday.cms.dashboard.web.mapper.UserProfileDtoMapper;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.Role;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.User;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.UserProfile;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.UserRole;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.UserProfileService;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.EUserSortBy;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.DataNotFoundException;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.PasswordNotMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserProfileServiceImpl implements UserProfileService {

    private final PasswordEncoder encoder;

    private final UserProfileDao userProfileDao;
    private final RoleDao roleDao;

    private final UserProfileDtoMapper userProfileDtoMapper;
    private final RoleMapper roleMapper;

    @Override
    public AddUserProfileDto addUserProfile(AddUserProfileDto userProfileDto) {
        if (!Objects.equals(userProfileDto.getPassword(), userProfileDto.getConfirmPassword()))
            throw new PasswordNotMatchException(IApplicationConstant.CommonMessage.ErrorMessage.ERROR_NEW_PASSWORD_NOT_MATCH);

        Role role = roleMapper.convertRoleDto(userProfileDto.getRoleDto());
        User user = userProfileDtoMapper.convertUser(userProfileDto);
        UserRole userRole = new UserRole(null, user, role);

        userRole.setCreatedDate(new Date());
        userRole.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        user.setUserRoles(Collections.singletonList(userRole));
        user.setPassword(encoder.encode(userProfileDto.getPassword()));
        user.setCreatedDate(new Date());
        user.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        UserProfile userProfile = userProfileDtoMapper.convertUserProfile(userProfileDto);
        userProfile.setUser(user);
        userProfile.setCreatedDate(new Date());
        userProfile.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        return userProfileDtoMapper.convertAddUserProfileDto(userProfileDao.save(userProfile));
    }

    @Override
    public UserProfileDetailDto findUserById(Long userProfileId) {
        return userProfileDtoMapper.convertUserProfileDetailDto(userProfileDao.findById(userProfileId)
                .orElseThrow(() -> new DataNotFoundException(
                IApplicationConstant.CommonMessage.ErrorMessage.ERROR_MESSAGE_DATA_NOT_FOUND)
        ));
    }

    @Override
    public UserProfileDetailDto updateUserProfile(Authentication authentication, EditUserProfileDto editUserProfileDto) {
        UserProfile userProfile = userProfileDao.findById(editUserProfileDto.getId()).orElseThrow(
                () -> new DataNotFoundException(IApplicationConstant.CommonMessage.ErrorMessage.ERROR_MESSAGE_USER_NOT_FOUND)
        );

        List<UserRole> userRoles = userProfile.getUser().getUserRoles().stream().map(
                ur -> {
                    if (Objects.equals(ur.getId(), userProfile.getUser().getUserRoles().stream().findFirst().orElseThrow().getId())) {
                        Role role = roleDao.findById(editUserProfileDto.getRoleDto().getId()).orElse(ur.getRole());

                        ur.setRole(role);
                        ur.setModifiedDate(new Date());
                        ur.setModifiedBy(authentication.getName());

                        return ur;
                    } else return ur;
                }
        ).distinct().collect(Collectors.toList());

        userProfile.getUser().setUserRoles(userRoles);
        userProfile.setDateOfBirth(editUserProfileDto.getDateOfBirth());
        userProfile.setName(editUserProfileDto.getName());
        userProfile.setIdCardNumber(editUserProfileDto.getIdCardNumber());
        userProfile.setPlaceOfBirth(editUserProfileDto.getPlaceOfBirth());
        userProfile.setGender(editUserProfileDto.getGender());
        userProfile.getUser().setUsername(editUserProfileDto.getUsername().trim());
        userProfile.setMobilePhoneNumber(editUserProfileDto.getMobilePhoneNumber());
        userProfile.setEmail(editUserProfileDto.getEmail());
        userProfile.setPhotoUrl(editUserProfileDto.getPhotoUrl());
        userProfile.setMemberLevel(editUserProfileDto.getMemberLevel());

        return userProfileDtoMapper.convertUserProfileDetailDto(userProfileDao.save(userProfile));
    }

    @Override
    public Page<UserProfileDto> getAllPaging(
            String searchName, String searchUsername, String searchDate, Integer page, Integer size, EUserSortBy sortBy, boolean isAscendingSort
    ) {
        Date searchDateFormatted = null;
        if (Objects.nonNull(searchDate)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                searchDateFormatted = dateFormat.parse(searchDate);
            } catch (ParseException e) {
                log.info("Handled Date Parsing Exception : " + searchDate);
            }
        }

        Sort sort = Sort.unsorted();
        if (Objects.nonNull(sortBy)) {
            sort = Sort.by(sortBy.getName());
            if (isAscendingSort) sort.ascending();
        }

        Pageable pageable = PageRequest.of(
                Optional.ofNullable(page).orElse(IApplicationConstant.CommonValue.Pagination.DEFAULT_PAGE_NUMBER) -
                        IApplicationConstant.CommonValue.Pagination.DEFAULT_PAGE_NUMBER,
                Optional.ofNullable(size).orElse(IApplicationConstant.CommonValue.Pagination.DEFAULT_PAGE_SIZE), sort
        );

        Page<UserProfile> userProfiles = userProfileDao.findAll(specificationUserProfile(searchName, searchUsername, searchDateFormatted), pageable);

        if (userProfiles.getTotalElements() == 0)
            throw new DataNotFoundException(IApplicationConstant.CommonMessage.ErrorMessage.ERROR_MESSAGE_DATA_NOT_FOUND);
        else return userProfiles.map(userProfileDtoMapper::convertUserProfileDto);
    }

    private Specification<UserProfile> specificationUserProfile(String searchName, String searchUsername, Date searchDate) {
        return (root, query, criteriaBuilder) -> {
            Join<UserProfile, User> userJoin = root.join("user", JoinType.LEFT);
            Predicate userPredicate = criteriaBuilder.conjunction();
            Predicate userProfilePredicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(searchDate))
                userProfilePredicate = criteriaBuilder.equal(root.get("dateOfBirth"), searchDate);

            if (Objects.nonNull(searchName))
                userProfilePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), '%' + searchName.toLowerCase() + '%');

            if (Objects.nonNull(searchUsername))
                userPredicate = criteriaBuilder.like(criteriaBuilder.lower(userJoin.get("username")), '%' + searchUsername.toLowerCase() + '%');

            return criteriaBuilder.and(userPredicate, userProfilePredicate);
        };
    }

}
