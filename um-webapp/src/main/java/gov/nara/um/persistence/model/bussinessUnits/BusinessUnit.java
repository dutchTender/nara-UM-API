package gov.nara.um.persistence.model.bussinessUnits;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import gov.nara.common.interfaces.INameableDto;
import gov.nara.common.persistence.model.INameableEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Entity
@Data
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "business_unit_catalog", schema = "oif_ods")
//@Table(name = "business_unit_catalog")
public class BusinessUnit  implements INameableEntity, INameableDto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bu_seq_gen")
    @SequenceGenerator(name = "bu_seq_gen", sequenceName = "oif_ods.business_unit_seq", allocationSize=1)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column
    private String org_code;

    @Column( name="ldap_id")
    private String ldapName;

    @OneToMany(
            mappedBy = "businessUnitID",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BusinessUnitConfigurationPreference> businessUnitConfigurationPreferences = new ArrayList<>();


    public BusinessUnitConfigurationPreference addBusinessUnitConfigurationPreference(BusinessUnitConfigurationPreference businessUnitConfigurationPreference){
        businessUnitConfigurationPreferences.add(businessUnitConfigurationPreference);
        return  businessUnitConfigurationPreference;
    }

    public void removeBusinessUnitConfigurationPreference( BusinessUnitConfigurationPreference businessUnitConfigurationPreference){
        for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = businessUnitConfigurationPreferences.iterator(); iterBUCP.hasNext(); ) {
            BusinessUnitConfigurationPreference current = iterBUCP.next();
            if(current.equals(businessUnitConfigurationPreference)){
                iterBUCP.remove();
            }
        }
    }




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

    public List<BusinessUnitConfigurationPreference> getBusinessUnitConfigurationPreferences() {
        return businessUnitConfigurationPreferences;
    }

    public void setBusinessUnitConfigurationPreferences(List<BusinessUnitConfigurationPreference> businessUnitConfigurationPreferences) {
        this.businessUnitConfigurationPreferences = businessUnitConfigurationPreferences;
    }
}
