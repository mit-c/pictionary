package org.main.drawing;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"https://mvnrepository.com/"}, allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.PUT}, maxAge = 60L)
@RequiredArgsConstructor
@RequestMapping("/game/{roundId}/drawing")
public class DrawingController {
    @Autowired
    private final DrawingService drawingService;

    @PutMapping("/event")
    public ResponseEntity<UUID> addDrawingEvent(@PathVariable UUID roundId, @RequestBody ArrayList<Point> points) {
        return drawingService.addEvent(roundId, points).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/event/all")
    @Transactional
    public ResponseEntity<Map<UUID, List<Point>>> getAllDrawingEvents(@PathVariable UUID roundId) {
        return drawingService.getAllPoints(roundId).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/event/all/flat")
    @Transactional
    public ResponseEntity<List<Point>> getDrawingFlat(@PathVariable UUID roundId, @RequestParam Integer index) {
        Optional<List<Point>> optionalPoints = drawingService.getAllPointsFlat(roundId);

        return optionalPoints
                .map(points -> ResponseEntity.ok(points.subList(index, points.size())))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}