package gov.nara.um.persistence.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import gov.nara.common.interfaces.INameableDto;
import gov.nara.common.persistence.model.INameableEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;



@Entity
@Data
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@Table(name = "business_unit_catalog", schema = "oif_ods")
@Table(name = "business_unit_catalog2")   /*annotation for embeded h2 db   */
public class BusinessUnit  implements INameableEntity, INameableDto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bu_seq_gen")
    @SequenceGenerator(name = "bu_seq_gen", sequenceName = "business_unit_seq")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column
    private String org_code;

    @Column( name="ldap_id")
    private String ldapName;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;

    }

    @Override
    public String getName() {
       String returnValue = name;

       return returnValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getLdapName() {
        return ldapName;
    }

    public void setLdapName(String ldapName) {
        this.ldapName = ldapName;
    }


}
