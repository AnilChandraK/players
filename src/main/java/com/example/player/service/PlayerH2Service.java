package com.example.player.service;

import com.example.player.model.Player;
import com.example.player.model.PlayerRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;

@Service
public class PlayerH2Service {

    @Autowired
    private JdbcTemplate db;

    public ArrayList<Player> getPlayer() {
        return (ArrayList<Player>) db.query("select * from team", new PlayerRowMapper());
    }
    
    public Player getPlayerById(int playerId) {
        try {
            return db.queryForObject("select * from team where playerId = ?", new PlayerRowMapper(), playerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    
    public Player addPlayer(Player player) {
        db.update("insert into team(playerName,jerseyNumber,role) values (?,?,?)", player.getPlayerName(),
                player.getJerseyNumber(), player.getRole());
        return db.queryForObject("select * from team where playerName = ? and jerseyNumber = ?", new PlayerRowMapper(),
                player.getPlayerName(), player.getJerseyNumber());
    }

    public void deletePlayer(int playerId) {
        db.update("delete from team where playerId = ?", playerId);
    }

    public Player updatePlayer(int playerId, Player player) {
        if (player.getPlayerName() != null) {
            db.update("update team set playerName = ? where playerId =?", player.getPlayerName(), playerId);
        }
        if (player.getJerseyNumber() != 0) {
            db.update("update team set jerseyNumber = ? where playerId =?", player.getJerseyNumber(), playerId);
        }
        if (player.getRole() != null) {
            db.update("update team set role = ? where playerId =?", player.getRole(), playerId);
        }
        return getPlayerById(playerId);
    }

}
