package gov.nara.um.persistence.dto.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitDTO;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupDTO;

import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({ "id", "name", "user_type", "business_units", "preservation_groups" })
public class UserDTO {

    private Long Id;

    private String name;

    private String user_type;

    private List<BusinessUnitDTO> business_units = new ArrayList<>();
    public void addBusinssUnitDTO(BusinessUnitDTO businessUnitDTO){ business_units.add(businessUnitDTO);}

    private List<PreservationGroupDTO> preservation_groups = new ArrayList<>();
    public void addPreservationGroupDTO(PreservationGroupDTO preservationGroupDTO){
        preservation_groups.add(preservationGroupDTO);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

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

    public List<BusinessUnitDTO> getBusiness_units() {
        return business_units;
    }

    public void setBusiness_units(List<BusinessUnitDTO> business_units) {
        this.business_units = business_units;
    }

    public List<PreservationGroupDTO> getPreservation_groups() {
        return preservation_groups;
    }

    public void setPreservation_groups(List<PreservationGroupDTO> preservation_groups) {
        this.preservation_groups = preservation_groups;
    }


}
