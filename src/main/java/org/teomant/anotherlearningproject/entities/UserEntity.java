package org.teomant.anotherlearningproject.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.teomant.anotherlearningproject.game.FighterEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"id", "username"})
@Table(name = "app_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity {

    @Id
    @SequenceGenerator( name = "users_sequence", sequenceName = "app_user_user_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "users_sequence" )
    @Column(name = "user_id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username;

    @JsonIgnore
    @Column(name = "encryted_password", nullable = false)
    private String password;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable( name = "user_role",
            joinColumns = { @JoinColumn( name = "user_id", referencedColumnName = "user_id" ) },
            inverseJoinColumns = {
                    @JoinColumn( name = "role_id", referencedColumnName = "role_id" ) } )
    private List<RoleEntity> roles = new ArrayList<>();

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    protected Set<UserEntity> friends;

    @ManyToMany(mappedBy = "friends")
    protected Set<UserEntity> befriended;

    @Column(name = "enabled", nullable = false)
    private Integer enabled;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private FighterEntity fighterEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "from")
    private List<MessageEntity> sentMessages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "to")
    private List<MessageEntity> receivedMessages;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password=<censored>" +
                ", roles=" + roles.stream().map(RoleEntity::getRoleName).collect(Collectors.toList()).toString() +
                ", fighter=" + (fighterEntity != null ? fighterEntity.toString() : "none") +
                ", friends=" + (friends != null ? friends.toString() : "unknown") +
                '}';
    }

}
