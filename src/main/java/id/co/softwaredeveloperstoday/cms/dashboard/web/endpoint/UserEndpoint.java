package id.co.softwaredeveloperstoday.cms.dashboard.web.endpoint;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.AddUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.EditUserProfileDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.RequestChangePasswordDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.UserProfileDetailDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.UserProfileService;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.UserService;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.ERoleName;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.DataNotFoundException;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.PasswordNotMatchException;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.exception.UserNotAllowedException;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.rest.result.ResultDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.rest.result.ResultListDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.rest.result.builder.ResultBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        value = IApplicationConstant.RestVersion.User.USER_V1,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class UserEndpoint {

    private final UserService userService;
    private final UserProfileService userProfileService;

    @GetMapping(IApplicationConstant.RestVersion.User.VIEW_USER_PROFILE)
    public ResponseEntity<ResultDto<UserProfileDetailDto>> getUserProfile(
            @PathVariable(IApplicationConstant.CommonValue.CommonRestPath.ID) Long id
    ) {
        try {
            return ResultBuilderUtil.ok(userProfileService.findUserById(id));
        } catch (DataNotFoundException e) {
            return ResultBuilderUtil.notFound(e.getMessage());
        }
    }

    @PostMapping(IApplicationConstant.RestVersion.User.CHANGE_PASSWORD)
    public ResponseEntity<ResultDto<String>> changePassword(
            Authentication authentication, @RequestBody @Valid RequestChangePasswordDto changePasswordDto
    ) {
        try {
            return ResultBuilderUtil.ok(userService.changePassword(authentication, changePasswordDto));
        } catch (UsernameNotFoundException | UserNotAllowedException | PasswordNotMatchException e) {
            return ResultBuilderUtil.badRequest(
                    IApplicationConstant.CommonValue.RestResponseValue.FAILED, e.getMessage()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBuilderUtil.internalServerError(e.getMessage());
        }
    }

    @GetMapping(IApplicationConstant.RestVersion.User.CURRENT_ROLE)
    public ResponseEntity<ResultListDto<ERoleName>> getCurrentRoles(Authentication authentication) {
        return ResultBuilderUtil.ok(authentication.getAuthorities().stream().map(r -> ERoleName.valueOf(String.valueOf(r))).collect(Collectors.toList()));
    }

    @GetMapping(IApplicationConstant.RestVersion.User.CHANGE_PASSWORD_ALLOWED_USER)
    public ResponseEntity<ResultListDto<ERoleName>> getAllowedUserToChangePassword(Authentication authentication) {
        return ResultBuilderUtil.ok(List.of(ERoleName.SUPER_ADMIN));
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResultDto<UserProfileDetailDto>> updateUser(
            Authentication authentication, @Valid @RequestBody EditUserProfileDto editUserProfileDto
    ) {
        try {
            return ResultBuilderUtil.ok(userProfileService.updateUserProfile(authentication, editUserProfileDto));
        } catch (DataNotFoundException e) {
            return ResultBuilderUtil.notFound(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBuilderUtil.internalServerError(e.getMessage());
        }
    }

}
