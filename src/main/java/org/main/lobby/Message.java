package org.main.lobby;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.main.lobby.Lobby;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@SuperBuilder
public class Message {
    public Message() {
        this.lobby = new Lobby();
        this.player = new Player();
        this.message = null;
        this.timestampUTC = null;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UUID id;

    @Getter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private Lobby lobby;

    @Column
    @Getter
    private String message;

    @Column
    @Getter
    private ZonedDateTime timestampUTC;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private Player player;
}
