package org.main.drawing;

import org.main.drawing.DrawingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DrawingEventRepository extends JpaRepository<DrawingEvent, UUID> {
}
