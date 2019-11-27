package gov.nara.um.persistence.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({ "id", "name", "user_type", "business_units" })
public class UserDTO {

    private Long Id;

    private String name;

    private String user_type;

    private List<BusinessUnitDTO> business_units = new ArrayList<>();

    public void addBusinssUnitDTO(BusinessUnitDTO businessUnitDTO){ business_units.add(businessUnitDTO);}

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
}
