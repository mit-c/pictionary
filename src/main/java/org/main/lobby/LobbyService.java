package org.main.lobby;

import org.main.game.Game;
import org.main.game.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Stream;

import static java.time.ZoneOffset.UTC;

@Service
public class LobbyService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    LobbyRepository lobbyRepository;

    public Lobby newLobby(Game game) {
        Lobby lobby = Lobby.builder()
                .build();
        // initially we create a lobby of the player who created the lobby
        // so the player is the drawer by default
        lobby.setPlayers(List.of());
        lobby.setMessages(List.of());
        lobby.setGame(game);

        lobbyRepository.save(lobby);
        return lobby;
    }

    public Player newPlayer(Lobby lobby, boolean isDrawer) {
       return Player.builder()
               .isDrawer(isDrawer)
               .messages(List.of())
               .lobby(lobby)
               .build();
    }

    private Message newMessage(Lobby lobby, Player player, String message) {
        return Message.builder()
                .lobby(lobby)
                .message(message)
                .player(player)
                .timestampUTC(ZonedDateTime.now(UTC))
                .build();
    }

    public Optional<Message> sendMessage(UUID lobbyId, UUID playerId, String message) {
        Optional<Lobby> optionalLobby = getLobby(lobbyId);
        if(optionalLobby.isEmpty()) {
            return Optional.empty();
        }
        Optional<Player> optionalPlayer = optionalLobby
                .get()
                .getPlayers()
                .stream()
                .filter(candidatePlayer -> candidatePlayer.getId().equals(playerId))
                .findFirst();
        if(optionalPlayer.isEmpty()) {
            return Optional.empty();
        }
        List<Message> messages = optionalPlayer.get().getMessages();
        Message newMessage = newMessage(optionalLobby.get(), optionalPlayer.get(), message);
        messages.add(newMessage);
        lobbyRepository.save(optionalLobby.get());
        return Optional.of(newMessage);
    }
    public Optional<Player> joinLobby(UUID lobbyId) {
        Optional<Lobby> optionalLobby = lobbyRepository.findById(lobbyId);
        if(optionalLobby.isEmpty()) { return  Optional.empty(); }
        Lobby lobby = optionalLobby.get();
        List<Player> players = lobby.getPlayers();
        boolean isDrawer = players.stream().noneMatch(Player::getIsDrawer);
        Player player = newPlayer(lobby, isDrawer);
        players.add(player);
        lobbyRepository.save(lobby);
        return Optional.of(player);
    }

    public Optional<Player> swapRoles(UUID lobbyId, UUID playerId) {
        Optional<Lobby> optionalLobby = lobbyRepository.findById(lobbyId);
        if(optionalLobby.isEmpty()) { return  Optional.empty(); }
        Optional<Player> player =optionalLobby.get().getPlayers()
                .stream()
                .filter(candidate -> candidate.getId().equals(playerId))
                .findFirst();
        if(player.isEmpty()) { return Optional.empty(); }
        if(!player.get().getIsDrawer()) {
            boolean isACurrentDrawer = optionalLobby.get().getPlayers().stream()
                    .anyMatch(Player::getIsDrawer);
            if(!isACurrentDrawer) { return player; }
            player.get().setIsDrawer(true);
        } else {
            player.get().setIsDrawer(false);
        }
        lobbyRepository.save(optionalLobby.get());
        return player;
    }

    public Optional<Lobby> getLobby(UUID lobbyId) {
        return lobbyRepository.findById(lobbyId);
    }

    public Optional<List<Message>> getMessages(UUID lobbyId, UUID playerId) {
        Optional<Lobby> optionalLobby = lobbyRepository.findById(lobbyId);
        if(optionalLobby.isEmpty()) {
            return Optional.empty();
        }
        Optional<Player> optionalPlayer = optionalLobby.get().getPlayers().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst();

        return optionalPlayer.map(Player::getMessages);
    }

    public Optional<List<Message>> getAllMessages(UUID lobbyId) {
        Optional<Lobby> optionalLobby = lobbyRepository.findById(lobbyId);
        return optionalLobby.map(Lobby::getMessages);
    }
}
