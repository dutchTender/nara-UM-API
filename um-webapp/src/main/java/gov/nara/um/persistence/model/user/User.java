package gov.nara.um.persistence.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import gov.nara.common.interfaces.ILongNameableDto;
import gov.nara.common.persistence.model.ILongNameableEntity;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Data
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "user", schema = "oif_ods")
//@Table(name = "user")
public class User implements ILongNameableEntity, ILongNameableDto {


    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "oif_ods.user_user_id_seq", allocationSize=1)
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(name = "user_name")
    private String name;

    private String user_type;

    private Long businessunit_id;


    @OneToMany(
            mappedBy = "userID",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserBusinessUnit> userBusinessUnits;



    @OneToMany(
            mappedBy = "userID",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserPreservationGroup> userPreservationGroups;


    @OneToMany(
            mappedBy = "userID",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserRole> userRoles;



    public Set<UserBusinessUnit> getUserBusinessUnits() {
        return userBusinessUnits;
    }

    public void setUserBusinessUnits(Set<UserBusinessUnit> userBusinessUnits) {
        this.userBusinessUnits = userBusinessUnits;
    }
    public UserBusinessUnit addUserBusinessUnit(UserBusinessUnit userBusinessUnit){
        userBusinessUnits.add(userBusinessUnit);
        return userBusinessUnit;
    }
    public void removeUserBusienssUnit(UserBusinessUnit userBusinessUnit){
        userBusinessUnits.remove(userBusinessUnit);
    }



    public Set<UserPreservationGroup> getUserPreservationGroups() {
        return userPreservationGroups;
    }

    public void setUserPreservationGroups(Set<UserPreservationGroup> userPreservationGroups) {
        this.userPreservationGroups = userPreservationGroups;
    }

    public UserPreservationGroup addUserPreservationGroup(UserPreservationGroup userPreservationGroup){
        this.userPreservationGroups.add(userPreservationGroup);
        return userPreservationGroup;
    }
    public void removeUserPreservationGroup(UserPreservationGroup userPreservationGroup){
        this.userPreservationGroups.remove(userPreservationGroup);
    }


    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public UserRole addUserRole(UserRole userRole){
        this.userRoles.add(userRole);
        return userRole;
    }
    public void removeUserRole(UserRole userRole){
        this.userRoles.remove(userRole);
    }


    @Override
    public void setId(Long id) { this.id = id; }

    @Override
    public Long getId() { return this.id; }


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

    public Long getBusinessunit_id() {
        return businessunit_id;
    }

    public void setBusinessunit_id(Long businessunit_id) {
        this.businessunit_id = businessunit_id;
    }
}
