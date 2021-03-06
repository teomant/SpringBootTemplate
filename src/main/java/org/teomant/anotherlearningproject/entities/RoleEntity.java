package org.teomant.anotherlearningproject.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "app_role")
public class RoleEntity {

    @Id
    @SequenceGenerator( name = "roles_sequence", sequenceName = "app_role_role_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "roles_sequence" )
    @Column(name = "role_id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users = new ArrayList<>();

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", users=" + users.stream().map(UserEntity::getUsername).collect(Collectors.toList()).toString() +
                '}';
    }
}
