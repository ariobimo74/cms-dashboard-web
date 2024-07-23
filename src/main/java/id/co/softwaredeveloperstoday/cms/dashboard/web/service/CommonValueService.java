package id.co.softwaredeveloperstoday.cms.dashboard.web.service;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.RequestRecommendedUsernameDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.ResponseCommonEnumDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.EMemberLevel;

import java.util.List;

public interface CommonValueService {

    List<ResponseCommonEnumDto> getGenders();

    ResponseCommonEnumDto getDefaultMember(EMemberLevel memberLevel);

    List<String> getRecommendedUsername(RequestRecommendedUsernameDto recommendedUsernameDto);

}
