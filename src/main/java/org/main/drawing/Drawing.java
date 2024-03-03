package org.main.drawing;

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
@SuperBuilder(toBuilder = true)
public class Drawing {
    public Drawing() {
        this.events = new ArrayList<>();
        this.game = new Game();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UUID id;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DrawingEvent> events;

    @Getter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="session_id", referencedColumnName = "session_id")
    private Game game;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "processed_points")
    private List<Point> processedPoints;
}
