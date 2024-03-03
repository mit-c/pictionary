package org.main.lobby;

import org.apache.coyote.Response;
import org.apache.coyote.http11.filters.VoidInputFilter;
import org.main.game.Game;
import org.main.game.GameRepository;
import org.main.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/game/lobby")
public class LobbyController {
    @Autowired
    LobbyService lobbyService;

    @Autowired
    GameService gameService;

    @GetMapping("/{gameId}")
    public ResponseEntity<UUID> getLobbyIdByGameId(@PathVariable UUID gameId) {
        return gameService.getGame(gameId).map(Game::getLobby)
                .map(Lobby::getId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/join/{lobbyId}")
    public ResponseEntity<Player> joinLobby(@PathVariable UUID lobbyId) {
        Optional<Player> optionalPlayer = lobbyService.joinLobby(lobbyId);
        return optionalPlayer
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/message/{lobbyId}/{playerId}")
    public ResponseEntity<Void> sendMessage(@PathVariable UUID lobbyId, @PathVariable UUID playerId, @RequestBody String message) {
        Optional<Message> optionalMessage = lobbyService.sendMessage(lobbyId, playerId, message);
        if(optionalMessage.isEmpty()) {return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/message/{lobbyId}/{playerId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable UUID lobbyId, @PathVariable UUID playerId) {
        Optional<List<Message>> optionalMessages = lobbyService.getMessages(lobbyId, playerId);
        return optionalMessages
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/message/{lobbyId}/all")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable UUID lobbyId) {
        Optional<List<Message>> optionalLobby = lobbyService.getAllMessages(lobbyId);
        return optionalLobby
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}
