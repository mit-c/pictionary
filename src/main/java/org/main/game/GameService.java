package org.main.game;

import org.main.drawing.Drawing;
import org.main.drawing.DrawingService;
import org.main.lobby.Lobby;
import org.main.lobby.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    LobbyService lobbyService;

    @Autowired
    DrawingService drawingService;

    public UUID newGame() {

        Game game = Game.builder()
                .sessionId(UUID.randomUUID())
                .build();
        Drawing drawing = drawingService.newRound(game);
        Lobby lobby = lobbyService.newLobby(game);
        gameRepository.save(game.toBuilder()
                        .activeDrawing(drawing.getId())
                        .lobby(lobby)
                .build());
        return game.getId();
    }

    public Optional<UUID> getGameId(UUID gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if(optionalGame.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(gameId);
    }

    public Optional<Game> getGame(UUID gameId) {
        return gameRepository.findById(gameId);
    }

    public Optional<UUID> getRound(UUID gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        return optionalGame.map(Game::getActiveDrawing);
    }

    @Transactional
    public void deleteAll() {
        drawingService.deleteAll();
        gameRepository.deleteAll();
        lobbyService.deleteAll();
    }
}
