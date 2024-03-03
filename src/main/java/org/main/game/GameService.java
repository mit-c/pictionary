package org.main.game;

import org.main.drawing.Drawing;
import org.main.drawing.DrawingRepository;
import org.main.drawing.DrawingService;
import org.main.lobby.Lobby;
import org.main.lobby.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    DrawingService drawingService;

    @Autowired
    LobbyService lobbyService;

    @Autowired
    DrawingRepository drawingRepository;

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
}
