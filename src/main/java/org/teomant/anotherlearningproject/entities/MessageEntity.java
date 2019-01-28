package org.teomant.anotherlearningproject.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "messages")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageEntity {

    @Id
    @SequenceGenerator( name = "message_sequence", sequenceName = "messages_message_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "message_sequence" )
    @Column(name = "message_id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "message_time")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private UserEntity from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private UserEntity to;

    @Column(name = "text")
    @NotNull
    private String text;

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", time=" + time +
                ", from=" + from +
                ", to=" + to +
                ", text='" + text + '\'' +
                '}';
    }
}
