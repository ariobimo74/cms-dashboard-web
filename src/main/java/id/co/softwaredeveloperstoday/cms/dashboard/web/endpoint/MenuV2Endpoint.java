package id.co.softwaredeveloperstoday.cms.dashboard.web.endpoint;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.ResponseMenuDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.rest.result.ResultListDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.rest.result.builder.ResultBuilderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(
        value = IApplicationConstant.RestVersion.Menu.MENU_V2
)
public class MenuV2Endpoint {

    @GetMapping()
    public ResponseEntity<ResultListDto<ResponseMenuDto>> getMenuList() {
        return ResultBuilderUtil.ok(new ArrayList<>(List.of(
                new ResponseMenuDto("Dashboard", "", "/dashboard", new ArrayList<>()),
                new ResponseMenuDto("Data", "",  null, List.of(
                        new ResponseMenuDto("Data Karyawan", "",  "/master/karyawan", new ArrayList<>()),
                        new ResponseMenuDto("Data Pasien", "",  "/master/pasien", new ArrayList<>())
                )),
                new ResponseMenuDto("About CMS Klinik", "",  "/about", new ArrayList<>()),
                new ResponseMenuDto("Log Out", "",  "/logout", new ArrayList<>())
        )));
    }

}
