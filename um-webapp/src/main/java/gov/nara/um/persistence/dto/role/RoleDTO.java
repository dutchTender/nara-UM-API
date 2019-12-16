package gov.nara.um.persistence.dto.role;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "role_id", "role_name", "role_description" })
public class RoleDTO {

    private Long role_id;
    private String role_name;
    private String role_description;

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_description() {
        return role_description;
    }

    public void setRole_description(String role_description) {
        this.role_description = role_description;
    }
}
