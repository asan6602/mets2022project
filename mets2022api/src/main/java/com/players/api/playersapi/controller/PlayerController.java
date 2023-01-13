package com.players.api.playersapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.players.api.playersapi.model.Player;
import com.players.api.playersapi.persistence.PlayerDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for the Player resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("Players")
public class PlayerController {
    private static final Logger LOG = Logger.getLogger(PlayerController.class.getName());
    private PlayerDAO playerDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param playerDAO The {@link PlayerDAO Player Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public PlayerController(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Player Player} for the given id
     * 
     * @param id The id used to locate the {@link Player Player}
     * 
     * @return ResponseEntity with {@link Player Player} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable int id) {
        LOG.info("GET /players/" + id);
        try {
            Player Player = playerDAO.getPlayer(id);
            if (Player != null)
                return new ResponseEntity<Player>(Player,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Player Playeres}
     * 
     * @return ResponseEntity with array of {@link Player Player} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Player[]> getPlayers() {
        LOG.info("GET /players");
        try {
            Player[] Players = playerDAO.getPlayers();
            if (Players != null)
                return new ResponseEntity<Player[]>(Players,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Player Playeres} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Player Playeres}
     * 
     * @return ResponseEntity with array of {@link Player Player} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all Playeres that contain the text "ma"
     * GET http://localhost:8080/Playeres/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Player[]> searchPlayers(@RequestParam String name) {
        LOG.info("GET /Playeres/?name="+name);
        try {
            Player[] Players = playerDAO.findPlayers(name);
            if (Players != null)
                return new ResponseEntity<Player[]>(Players,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
