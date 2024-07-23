package id.co.softwaredeveloperstoday.cms.dashboard.web.endpoint;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.ResponseMenuDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(
        value = IApplicationConstant.RestVersion.Menu.MENU_V1
)
public class MenuEndpoint {

    @GetMapping()
    public ResponseEntity<List<ResponseMenuDto>> getMenuList() {
        return new ResponseEntity<>(new ArrayList<>(List.of(
                new ResponseMenuDto("Dashboard", "", "/dashboard", new ArrayList<>()),
                new ResponseMenuDto("Data", "",  null, List.of(
                        new ResponseMenuDto("Data Karyawan", "",  "/master/karyawan", new ArrayList<>()),
                        new ResponseMenuDto("Data Pasien", "",  "/master/pasien", new ArrayList<>())
                )),
                new ResponseMenuDto("About CMS Klinik", "",  "/about", new ArrayList<>()),
                new ResponseMenuDto("Log Out", "",  "/logout", new ArrayList<>())
        )), HttpStatus.OK);
    }

}
