package gov.nara.um.persistence.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import gov.nara.common.interfaces.ILongNameableDto;
import gov.nara.common.persistence.model.ILongNameableEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Data
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
//@Table(name = "user", schema = "oif_ods")
@Table(name = "user")
public class User implements ILongNameableEntity, ILongNameableDto {


    @Id
    @Column(name = "user_id")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    //@SequenceGenerator(name = "user_seq_gen", sequenceName = "oif_ods.user_user_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(name = "user_name")
    private String name;

    private String user_type;

    private Long businessunit_id;


    @JoinTable(
            name = "user_business_unit",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "business_unit_id",
                    referencedColumnName = "id"
            )
    )
    @OneToMany
    private Set<BusinessUnit> businessUnits;

    public Set<BusinessUnit> getBusinessUnits() {
        return businessUnits;
    }

    public BusinessUnit addBusinessUnit(BusinessUnit businessUnit){ businessUnits.add(businessUnit);return businessUnit; }

    public void removeBusinessUnit(BusinessUnit businessUnit){ businessUnits.remove(businessUnit); }

    public void setBusinessUnits(Set<BusinessUnit> businessUnits) {
        this.businessUnits = businessUnits;
    }

    @Override
    public void setId(Long id) { this.id = id; }

    @Override
    public Long getId() { return this.id; }


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public Long getBusinessunit_id() { return businessunit_id; }

    public void setBusinessunit_id(Long businessunit_id) { this.businessunit_id = businessunit_id; }
}
