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


    private String name;

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







}
