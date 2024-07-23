package id.co.softwaredeveloperstoday.cms.dashboard.web.endpoint;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.RequestRecommendedUsernameDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.ResponseCommonEnumDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.CommonValueService;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.EMemberLevel;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.rest.result.ResultDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.rest.result.ResultListDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.rest.result.builder.ResultBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = IApplicationConstant.RestVersion.CommonValue.COMMON_VALUE
)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonValueEndpoint {

    private final CommonValueService commonValueService;

    @GetMapping(IApplicationConstant.RestVersion.CommonValue.GENDER)
    public ResponseEntity<ResultListDto<ResponseCommonEnumDto>> getGenders() {
        return ResultBuilderUtil.ok(commonValueService.getGenders());
    }

    @GetMapping(IApplicationConstant.RestVersion.CommonValue.MEMBER)
    public ResponseEntity<ResultDto<ResponseCommonEnumDto>> getDefaultMember(
            @RequestParam(value = "name", required = false, defaultValue = "REGULAR") EMemberLevel memberLevel
    ) {
        return ResultBuilderUtil.ok(commonValueService.getDefaultMember(memberLevel));
    }

    @PatchMapping(IApplicationConstant.RestVersion.CommonValue.RECOMMENDED_USERNAME)
    public ResponseEntity<ResultListDto<String>> getRecommendedUsername(
            @RequestBody @Valid RequestRecommendedUsernameDto recommendedUsernameDto
    ) {
        return ResultBuilderUtil.ok(commonValueService.getRecommendedUsername(recommendedUsernameDto));
    }

}
