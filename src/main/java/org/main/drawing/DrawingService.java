package org.main.drawing;

import lombok.SneakyThrows;
import org.main.drawing.Drawing;
import org.main.drawing.DrawingEvent;
import org.main.game.Game;
import org.main.drawing.Point;
import org.main.drawing.DrawingEventRepository;
import org.main.drawing.DrawingRepository;
import org.main.game.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
@Service
public class DrawingService {
    @Autowired
    private DrawingEventRepository drawingEventRepository;

    @Autowired
    private DrawingRepository drawingRepository;

    @Autowired
    private GameRepository gameRepository;
    @SneakyThrows
    @Async
    public CompletableFuture<Drawing> processEvents(Drawing originalDrawing) {
        List<Point> processedEvents = originalDrawing.getEvents().stream()
                .map(DrawingEvent::getPoints)
                .flatMap(Collection::stream).toList();
        originalDrawing.setProcessedPoints(new ArrayList<>(processedEvents));
        return CompletableFuture.completedFuture(originalDrawing);
    }
    public Drawing newRound(Game game) {
        Drawing drawing = Drawing.builder()
                        .events(new ArrayList<>())
                .processedPoints(new ArrayList<>())
                .game(game)
                .build();
        drawingRepository.save(drawing);
        return drawing;
    }
    public Optional<UUID> addEvent(UUID drawingId, ArrayList<Point> points) {
        Optional<Drawing> drawing = drawingRepository.findById(drawingId);
        if (drawing.isEmpty()) {
            return Optional.empty();
        }

        DrawingEvent drawingEvent = DrawingEvent.builder()
                .drawing(drawing.get())
                .points(points)
                .build();
        for(Point point: points) {
            point.setDrawingEvent(drawingEvent);
        }
        drawing.get().getEvents().add(drawingEvent);
        processEvents(drawing.get()).thenAccept(processedDrawing -> drawingRepository.save(processedDrawing));
        return Optional.of(drawingEvent.getId());
    }

    public Optional<List<Point>> getAllPointsFlat(UUID drawingId) {
        Optional<Drawing> optionalDrawing = drawingRepository.findById(drawingId);
        return optionalDrawing.map(Drawing::getProcessedPoints);
    }
    public Optional<Map<UUID, List<Point>>> getAllPoints(UUID drawingId) {
        Optional<Drawing> optionalDrawing = drawingRepository.findById(drawingId);
        return optionalDrawing.map(game -> game.getEvents().stream()
                .collect(Collectors.toMap(
                        DrawingEvent::getId, DrawingEvent::getPoints
                )));

    }

}
