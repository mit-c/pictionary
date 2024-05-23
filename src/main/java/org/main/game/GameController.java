package org.main.game;

import lombok.RequiredArgsConstructor;
import org.main.drawing.DrawingService;
import org.main.lobby.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    DrawingService drawingService;

    @Autowired
    GameService gameService;

    @Autowired
    LobbyService lobbyService;

    @PutMapping("/new")
    @Transactional
    public ResponseEntity<UUID> newGame() {
        return ResponseEntity.ok(gameService.newGame());
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<UUID> getGame(@PathVariable UUID gameId) {
        return gameService.getGameId(gameId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{gameId}/round")
    public ResponseEntity<UUID> getRound(@PathVariable UUID gameId) {
        return gameService.getGame(gameId)
                .map(game -> ResponseEntity.ok(game.getActiveDrawing()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteAll() {
        gameService.deleteAll();
        return ResponseEntity.ok().build();
    }

}
