package id.co.softwaredeveloperstoday.cms.dashboard.web.service.impl;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dao.RoleDao;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dao.UserProfileDao;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.AddUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.EditUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.UserProfileDetailDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.mapper.RoleMapper;
import id.co.softwaredeveloperstoday.cms.dashboard.web.mapper.UserProfileDtoMapper;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.Role;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.User;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.UserProfile;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.UserRole;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.UserProfileService;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.DataNotFoundException;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.PasswordNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        userProfile.setAllergy(editUserProfileDto.getAllergy());
        userProfile.setMemberLevel(editUserProfileDto.getMemberLevel());

        return userProfileDtoMapper.convertUserProfileDetailDto(userProfileDao.save(userProfile));
    }

}
