package id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.address;

import id.co.softwaredeveloperstoday.cms.dashboard.web.model.CommonAudit;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "region", schema = IApplicationConstant.SchemaName.MASTER_ADDRESS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = IApplicationConstant.CommonValue.DefaultEntityWhereClause.ENTITY_WHERE_CLAUSE)
public class Region extends CommonAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IApplicationConstant.SchemaName.MASTER_ADDRESS + ".region_id_seq")
    @SequenceGenerator(name = IApplicationConstant.SchemaName.MASTER_ADDRESS + ".region_id_seq",
            sequenceName = IApplicationConstant.SchemaName.MASTER_ADDRESS + ".region_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "region", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private List<Province> provinces;

}
