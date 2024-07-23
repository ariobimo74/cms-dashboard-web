package id.co.softwaredeveloperstoday.cms.dashboard.web.endpoint;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.RequestChangePasswordDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.UserService;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.PasswordNotMatchException;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.UserNotAllowedException;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.rest.result.ResultDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.rest.result.builder.ResultBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = IApplicationConstant.RestVersion.User.USER_V1
)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserEndpoint {

    private final UserService userService;

    @PostMapping(IApplicationConstant.RestVersion.User.CHANGE_PASSWORD)
    public ResponseEntity<ResultDto<String>> changePassword(
            Authentication authentication, @RequestBody @Valid RequestChangePasswordDto changePasswordDto
    ) {
        try {
            return ResultBuilderUtil.ok(userService.changePassword(authentication, changePasswordDto));
        } catch (UserNotAllowedException | PasswordNotMatchException e) {
            return ResultBuilderUtil.internalServerError(
                    IApplicationConstant.CommonValue.RestResponseValue.FAILED, e.getMessage()
            );
        } catch (Exception e) {
            return ResultBuilderUtil.internalServerError(e.getMessage());
        }
    }

}
