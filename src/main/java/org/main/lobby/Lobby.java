package org.main.lobby;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.main.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@SuperBuilder
@Getter
public class Lobby {

    public Lobby() {
        this.game = new Game();
        this.players = new ArrayList<>();
        this.messages = new ArrayList<>();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UUID id;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @Setter
    @OneToMany(mappedBy = "lobby", cascade = CascadeType.ALL)
    private List<Player> players = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "lobby", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
}
