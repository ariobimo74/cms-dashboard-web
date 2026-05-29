package id.co.softwaredeveloperstoday.cms.dashboard.web.factory;

import id.co.softwaredeveloperstoday.cms.dashboard.web.service.AuthorizationRoleLevelService;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.impl.AuthorizationRoleLevelRegularAdminServiceImpl;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.impl.AuthorizationRoleLevelSuperAdminServiceImpl;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.ERoleLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class AuthorizationRoleLevelFactory {

    private final List<AuthorizationRoleLevelService> levelService;

    private final Map<ERoleLevel, AuthorizationRoleLevelService> roleLevelServiceMap = new HashMap<>();

    public AuthorizationRoleLevelService determineService(ERoleLevel roleLevel) {
        for (AuthorizationRoleLevelService service : levelService) {
            roleLevelServiceMap.put(service.determineRoleLevel(), service);
        }
        return roleLevelServiceMap.get(roleLevel);
    }

}
