package id.co.softwaredeveloperstoday.cms.dashboard.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class CommonAudit extends CommonEntity {

    @Column(name = "name")
    private String name;

}
