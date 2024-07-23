package id.co.softwaredeveloperstoday.cms.dashboard.web.service;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.RequestChangePasswordDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    User findUserByUserName(String username);

    List<User> getUserByUsernameIn(List<String> usernames);

    String changePassword(Authentication authentication, RequestChangePasswordDto changePasswordDto);

}
