package id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.address;

import id.co.softwaredeveloperstoday.cms.dashboard.web.model.CommonAudit;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.constant.IApplicationConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "city", schema = IApplicationConstant.SchemaName.MASTER_ADDRESS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = IApplicationConstant.CommonValue.DefaultEntityWhereClause.ENTITY_WHERE_CLAUSE)
public class City extends CommonAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IApplicationConstant.SchemaName.MASTER_ADDRESS +  ".city_id_seq")
    @SequenceGenerator(name = IApplicationConstant.SchemaName.MASTER_ADDRESS + ".city_id_seq",
            sequenceName = IApplicationConstant.SchemaName.MASTER_ADDRESS + ".city_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="province_id", referencedColumnName = "id")
    private Province province;

}
