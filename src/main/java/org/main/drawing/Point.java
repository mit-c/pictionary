package org.main.drawing;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.main.drawing.Drawing;
import org.main.drawing.DrawingEvent;

import java.util.UUID;

@Entity
@RequiredArgsConstructor
public class Point {
    public Point() {
        this.x = null;
        this.y = null;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = jakarta.persistence.CascadeType.ALL)
    @JoinColumn(name = "points")
    @Setter
    private DrawingEvent drawingEvent;

    @ManyToOne(fetch = FetchType.EAGER, cascade = jakarta.persistence.CascadeType.ALL)
    @JoinColumn(name = "processed_points")
    @Setter
    private Drawing drawing;
    @Column
    @Getter
    @Cascade(CascadeType.ALL)
    private final Integer x;
    @Column
    @Getter
    @Cascade(CascadeType.ALL)
    private final Integer y;
}
