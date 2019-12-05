package gov.nara.um.persistence.model.preservationGroup;

import gov.nara.common.interfaces.ILongNameableDto;
import gov.nara.common.persistence.model.ILongNameableEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "group", schema = "oif_ods")
//@Table(name = "\"group\"")
public class PreservationGroup implements ILongNameableEntity, ILongNameableDto {



    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq_gen")
    @SequenceGenerator(name = "group_seq_gen", sequenceName = "oif_ods.group_group_id_seq", allocationSize=1)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;


    @Column(name = "group_name")
    private String name;


    private String group_description;






    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
           this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }

/*
    @JoinTable(name = "group_permissions", joinColumns = {
            @JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "assigned_group_id", referencedColumnName = "group_id", nullable = false)

            })
    // adding the third join column causes issues when saving ....it throws error about updating an object and its id has changed
    @ManyToMany
    private List<PreservationGroup> inheritedGroups = new ArrayList<>();
*/



    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "preservationGroupID",
            fetch = FetchType.EAGER)
    private List<PreservationGroupPermission> inheritedGroups = new ArrayList<>();

/*
    @OneToMany(//cascade = CascadeType.ALL,
            mappedBy = "assigningGroupID",
            fetch = FetchType.EAGER)
    private List<PreservationGroupPermission> assignedGroups = new ArrayList<>();

*/

    public PreservationGroupPermission addGroupPermission(PreservationGroupPermission parentGroup){

       inheritedGroups.add(parentGroup);
       return parentGroup;
    }

    public void removeGroupPermission(PreservationGroupPermission group){
        for(Iterator<PreservationGroupPermission> iterPGP = inheritedGroups.iterator(); iterPGP.hasNext(); ) {
            PreservationGroupPermission current = iterPGP.next();
            if(current.equals(group)){
                iterPGP.remove();
            }
        }
    }



/*
    public PreservationGroupPermission addAssignGroupPermission(PreservationGroupPermission parentGroup){

        assignedGroups.add(parentGroup);
        return parentGroup;
    }

    public void removeAssignGroupPermission(PreservationGroupPermission group){
        for(Iterator<PreservationGroupPermission> iterPGP = assignedGroups.iterator(); iterPGP.hasNext(); ) {
            PreservationGroupPermission current = iterPGP.next();
            if(current.equals(group)){
                iterPGP.remove();
            }
        }
    }

*/
    public List<PreservationGroupPermission> getInheritedGroups() {
        return inheritedGroups;
    }

    public void setInheritedGroups(List<PreservationGroupPermission> inheritedGroups) {
        this.inheritedGroups = inheritedGroups;
    }

/*
    public List<PreservationGroupPermission> getAssignedGroups() {
        return assignedGroups;
    }

    public void setAssignedGroups(List<PreservationGroupPermission> assignedGroups) {
        this.assignedGroups = assignedGroups;
    }


*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreservationGroup)) return false;
        PreservationGroup that = (PreservationGroup) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
