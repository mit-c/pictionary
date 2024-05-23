package org.main.drawing;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.UUID;

@Entity
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
public class DrawingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UUID id;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "drawingEvent")
    @Column(name = "points")
    private List<Point> points;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Drawing drawing;

}
