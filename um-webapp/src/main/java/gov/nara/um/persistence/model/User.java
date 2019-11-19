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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bu_seq_gen")
    @SequenceGenerator(name = "bu_seq_gen", sequenceName = "business_unit_seq")
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(name = "user_name")
    private String name;


    private String user_type;


    @JoinTable(
            name = "user_business_unit",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
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

    public BusinessUnit addBusinessUnit(BusinessUnit businessUnit){

        businessUnits.add(businessUnit);
        return businessUnit;

    }

    public void removeBusinessUnit(BusinessUnit businessUnit){

        businessUnits.remove(businessUnit);
    }


    public void setBusinessUnits(Set<BusinessUnit> businessUnits) {
        this.businessUnits = businessUnits;
    }

    @Override
    public void setId(Long id) {

    }

    @Override
    public Long getId() {
        return null;
    }


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
}
