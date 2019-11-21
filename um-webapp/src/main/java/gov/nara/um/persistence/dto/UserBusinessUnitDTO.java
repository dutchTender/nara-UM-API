package gov.nara.um.persistence.dto;



public class UserBusinessUnitDTO   {



    private Long user_id;

    private Integer business_unit_id;


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getBusiness_unit_id() {
        return business_unit_id;
    }

    public void setBusiness_unit_id(Integer business_unit_id) {
        this.business_unit_id = business_unit_id;
    }
}
