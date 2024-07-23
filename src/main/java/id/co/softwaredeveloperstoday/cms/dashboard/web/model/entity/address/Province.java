package id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.address;

import id.co.softwaredeveloperstoday.cms.dashboard.web.model.CommonAudit;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "province", schema = IApplicationConstant.SchemaName.MASTER_ADDRESS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = IApplicationConstant.CommonValue.DefaultEntityWhereClause.ENTITY_WHERE_CLAUSE)
public class Province extends CommonAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IApplicationConstant.SchemaName.MASTER_ADDRESS + ".province_id_seq")
    @SequenceGenerator(name = IApplicationConstant.SchemaName.MASTER_ADDRESS + ".province_id_seq",
            sequenceName = IApplicationConstant.SchemaName.MASTER_ADDRESS + ".province_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="region_id", referencedColumnName = "id")
    private Region region;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "province", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private List<City> cities;

}
