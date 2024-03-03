package org.main.drawing;

import org.main.drawing.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DrawingRepository extends JpaRepository<Drawing, UUID> {
}
