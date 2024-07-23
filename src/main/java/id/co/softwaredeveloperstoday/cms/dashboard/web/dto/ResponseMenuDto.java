package id.co.softwaredeveloperstoday.cms.dashboard.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMenuDto {

    private String menuName;
    private String iconUrl;
    private String url;
    private List<ResponseMenuDto> subMenu;

}
