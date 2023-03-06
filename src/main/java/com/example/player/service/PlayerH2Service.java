/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.player.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.*;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

import com.example.player.model.Player;
import com.example.player.model.PlayerRowMapper;

import com.example.player.repository.PlayerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlayerH2Service implements PlayerRepository{
    int uniquePlayerId = 12;
    @Autowired
    private JdbcTemplate db;
    @Override
    public ArrayList<Player> getPlayer(){
        List<Player> playerList = db.query("select* from TEAM", new PlayerRowMapper());
        ArrayList<Player> players = new ArrayList<>(playerList);
        return players;
    }
    


    @Override
    public Player getPlayerById(int playerId){
        String sql = "select * from TEAM where id = ?" ;
        Player player = db.queryForObject(sql, new PlayerRowMapper(), playerId);
        return player;
    }

    
    @Override
    public Player addPlayer(Player player){
        player.setPlayerId(uniquePlayerId);
        db.update("insert into player(playerName,jerseyNumber,role) values (?,?,?)",player.getPlayerName(), player.getJerseyNumber(),player.getRole());
        return db.queryForObject("select * from TEAM where playerName = ? and jerseyNumber = ? and role = ?", new PlayerRowMapper(), player.getPlayerName(), player.getJerseyNumber(), player.getRole());
    }

   
   
    ////////////////
    @Override
    public Player updatePlayer(int playerId, Player player){
        String sql = "select * from TEAM where id = ?" ;
        Player existingPlayer = db.queryForObject(sql, new PlayerRowMapper(), playerId);
        if(existingPlayer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(existingPlayer.getRole()!=null){
            db.update("update TEAM set role = ? where id =?", player.getRole(),playerId);
            }
        if(existingPlayer.getJerseyNumber()!=0){
            db.update("update TEAM set jerseyNumber = ? where id =?", player.getJerseyNumber(),playerId);
            }
        if(existingPlayer.getPlayerName()!=null){
            db.update("update TEAM set playerName = ? where id =?", player.getPlayerName(),playerId);
            }
        return existingPlayer;
    }
    
    
    @Override
    public void deletePlayer(int playerId){
        String sql = "select * from TEAM where id = ?" ;
        Player player = db.queryForObject(sql, new PlayerRowMapper(), playerId);
        if(player == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        else{
            db.update("delete from TEAM where id = ?", playerId);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        
    }


}









   

