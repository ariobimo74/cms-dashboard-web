package id.co.softwaredeveloperstoday.cms.dashboard.web.controller;

import id.co.softwaredeveloperstoday.cms.dashboard.web.scope.UserScope;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonController {

    private final UserScope userScope;

    @GetMapping("/login")
    public String loginPage() {
        if (Objects.isNull(userScope) || Objects.isNull(userScope.getUser())
                || Objects.isNull(userScope.getUser().getUsername()))
            return "login/login";
        else return "dashboard";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "login/register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/change_password")
    public String forgotPasswordPage() {return "login/change_password";}

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        String cookiePath = StringUtils.hasText(request.getContextPath()) ? request.getContextPath() : "/";
        cookie.setPath(cookiePath);
        cookie.setMaxAge(0);
        cookie.setSecure(request.isSecure());
        response.addCookie(cookie);

        return "login/login";
    }

    @GetMapping("/error/404")
    public String handleError() {
        return "error/404";
    }

    @GetMapping(IApplicationConstant.RestVersion.User.VIEW_USER_PROFILE)
    public String viewUserProfile() {
        return "user/detail";
    }

    @GetMapping(IApplicationConstant.RestVersion.User.EDIT_USER_PROFILE)
    public String editUserProfile() {
        return "user/edit";
    }

    @GetMapping(IApplicationConstant.RestVersion.User.VIEW_USER_PROFILES)
    public String viewUserProfiles() {
        return "user/table";
    }

}
