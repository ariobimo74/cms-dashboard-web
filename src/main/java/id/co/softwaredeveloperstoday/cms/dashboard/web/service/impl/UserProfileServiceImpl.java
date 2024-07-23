package id.co.softwaredeveloperstoday.cms.dashboard.web.service.impl;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dao.UserProfileDao;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.AddUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.mapper.RoleMapper;
import id.co.softwaredeveloperstoday.cms.dashboard.web.mapper.UserProfileDtoMapper;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.Role;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.User;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.UserProfile;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.UserRole;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileDao userProfileDao;

    private final UserProfileDtoMapper userProfileDtoMapper;
    private final RoleMapper roleMapper;

    @Override
    public AddUserProfileDto addUserProfile(AddUserProfileDto userProfileDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

}
