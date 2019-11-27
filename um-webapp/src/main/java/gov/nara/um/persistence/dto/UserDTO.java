package gov.nara.um.persistence.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private Long Id;

    private String name;

    private String user_type;

    private List<BusinessUnitDTO> businessUnits = new ArrayList<>();

    public void addBusinssUnitDTO(BusinessUnitDTO businessUnitDTO){ businessUnits.add(businessUnitDTO);}

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

    public List<BusinessUnitDTO> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(List<BusinessUnitDTO> businessUnits) {
        this.businessUnits = businessUnits;
    }
}
