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
//@Table(name = "group", schema = "oif_ods")
@Table(name = "\"group\"")
public class PreservationGroup implements ILongNameableEntity, ILongNameableDto {



    @Id
    @Column(name = "group_id")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq_gen")
    //@SequenceGenerator(name = "group_seq_gen", sequenceName = "oif_ods.group_group_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO)
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


    @OneToMany(
            mappedBy = "preservationGroupID",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PreservationGroupPermission> preservationGroupPermissions = new ArrayList<>();



    public PreservationGroupPermission addGroupPermission(PreservationGroupPermission preservationGroupPermission){
        preservationGroupPermissions.add(preservationGroupPermission);

        return preservationGroupPermission;
    }

    public void removeGroupPermission(PreservationGroupPermission preservationGroupPermission){
        for(Iterator<PreservationGroupPermission> iterPGP = preservationGroupPermissions.iterator(); iterPGP.hasNext(); ) {
            PreservationGroupPermission current = iterPGP.next();
            if(current.equals(preservationGroupPermission)){
                iterPGP.remove();
            }
        }
    }




    public List<PreservationGroupPermission> getPreservationGroupPermissions() {
        return preservationGroupPermissions;
    }

    public void setPreservationGroupPermissions(List<PreservationGroupPermission> preservationGroupPermissions) {
        this.preservationGroupPermissions = preservationGroupPermissions;
    }



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
