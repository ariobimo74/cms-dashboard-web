package id.co.softwaredeveloperstoday.cms.dashboard.web.service.impl;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dao.UserDao;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.RequestChangePasswordDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.User;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.UserService;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.ERoleName;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.PasswordNotMatchException;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.UserNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public User findUserByUserName(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> getUserByUsernameIn(List<String> usernames) {
        return userDao.findAll(specificationUsernameIn(usernames));
    }

    @Override
    public String changePassword(Authentication authentication, RequestChangePasswordDto changePasswordDto) {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.toString().equals(ERoleName.SUPER_ADMIN.toString()))
                && !changePasswordDto.getUsername().equals(authentication.getName()))
            throw new UserNotAllowedException(IApplicationConstant.CommonMessage.ErrorMessage.ERROR_MESSAGE_USER_NOT_ALLOWED);

        User user = userDao.findByUsername(changePasswordDto.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(changePasswordDto.getNewPassword());

        if (encoder.matches(encodedPassword, user.getPassword()))
            throw new PasswordNotMatchException(IApplicationConstant.CommonMessage.ErrorMessage.ERROR_OLD_PASSWORD_NOT_MATCH);

        user.setPassword(encodedPassword);
        user.setModifiedDate(new Date());
        user.setModifiedBy(authentication.getName());
        user.setPasswordmodifiedDate(new Date());
        user.setPasswordmodifiedBy(authentication.getName());

        userDao.save(user);

        return IApplicationConstant.CommonValue.RestResponseValue.SUCCESS;
    }

    private Specification<User> specificationUsernameIn(List<String> usernames) {
        return ((root, query, criteriaBuilder) -> root.get("username").in(usernames));
    }

}
