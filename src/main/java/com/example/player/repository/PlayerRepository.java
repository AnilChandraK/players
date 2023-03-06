package com.example.player.repository;

import java.util.ArrayList;

import com.example.player.model.Player;

public interface PlayerRepository {
    ArrayList<Player> getPlayer();

    Player getPlayerById(int playerId);
    
    Player addPlayer(Player player);
    
    Player updatePlayer(int playerId, Player player);
    
    void deletePlayer(int playerId);

}

