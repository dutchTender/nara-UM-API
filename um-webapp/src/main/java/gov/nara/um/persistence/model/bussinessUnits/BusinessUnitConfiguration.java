package gov.nara.um.persistence.model.bussinessUnits;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import gov.nara.common.interfaces.ILongNameableDto;

import gov.nara.common.persistence.model.ILongNameableEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "business_unit_configuration", schema = "oif_ods")
//@Table(name = "business_unit_configuration")
public class BusinessUnitConfiguration implements ILongNameableEntity, ILongNameableDto {


    @Id
    @Column(name = "configuration_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bu_conf_seq_gen")
    @SequenceGenerator(name = "bu_conf_seq_gen", sequenceName = "oif_ods.business_unit_configuration_configuration_id_seq", allocationSize=1)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(name = "configuration_name", unique = true, nullable = false)
    private String name;







    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


}
