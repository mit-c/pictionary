package org.main.game;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.main.drawing.Drawing;
import org.main.lobby.Lobby;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(name = "session_id", unique = true)
    private UUID sessionId;


    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Drawing> drawings;  // Link based on the sessionId attribute


    private UUID activeDrawing;


    @OneToOne(mappedBy = "game")
    private Lobby lobby;
}
