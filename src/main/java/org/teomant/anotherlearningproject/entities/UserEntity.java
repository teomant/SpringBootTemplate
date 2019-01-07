package org.teomant.anotherlearningproject.entities;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "app_user")
public class UserEntity {

    @Id
    @SequenceGenerator( name = "users_sequence", sequenceName = "app_user_user_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "users_sequence" )
    @Column(name = "user_id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "encryted_password", nullable = false)
    private String password;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable( name = "user_role",
            joinColumns = { @JoinColumn( name = "user_id", referencedColumnName = "user_id" ) },
            inverseJoinColumns = {
                    @JoinColumn( name = "role_id", referencedColumnName = "role_id" ) } )
    private List<RoleEntity> roles = new ArrayList<>();

    @Column(name = "enabled", nullable = false)
    private Integer enabled;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password=<censored>" +
                ", roles=" + roles.stream().map(RoleEntity::getRoleName).collect(Collectors.toList()).toString() +
                '}';
    }
}
