package org.main.lobby;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.main.lobby.Lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
public class Player {
    public Player() {
        this.lobby = new Lobby();
        this.messages = new ArrayList<>();
        this.isDrawer = null;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UUID id;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Lobby lobby;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;

    @Column
    private Boolean isDrawer;
}
