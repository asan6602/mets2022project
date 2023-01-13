package com.players.api.playersapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.players.api.playersapi.model.Player;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implements the functionality for JSON file-based peristance for Heroes
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 */
@Component
public class PlayerFileDAO implements PlayerDAO {
    private static final Logger LOG = Logger.getLogger(PlayerFileDAO.class.getName());
    Map<Integer,Player> players;   // Provides a local cache of the player objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Player
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new player
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Hero File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public PlayerFileDAO(@Value("${players.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the heroes from the file
    }

    /**
     * Generates the next id for a new {@linkplain Player hero}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Player players} from the tree map
     * 
     * @return  The array of {@link Player players}, may be empty
     */
    private Player[] getPlayersArray() {
        return getPlayersArray(null);
    }

    /**
     * Generates an array of {@linkplain Player players} from the tree map for any
     * {@linkplain Player players} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Player players}
     * in the tree map
     * 
     * @return  The array of {@link Player players}, may be empty
     */
    private Player[] getPlayersArray(String containsText) { // if containsText == null, no filter
        ArrayList<Player> playerArrayList = new ArrayList<>();

        for (Player player : players.values()) {
            if (containsText == null || player.getName().contains(containsText)) {
                playerArrayList.add(player);
            }
        }

        Player[] playerArray = new Player[playerArrayList.size()];
        playerArrayList.toArray(playerArray);
        return playerArray;
    }

    /**
     * Saves the {@linkplain Player player} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Player player} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Player[] playerArray = getPlayersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),playerArray);
        return true;
    }

    /**
     * Loads {@linkplain Player player} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        players = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of heroes
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Player[] playerArray = objectMapper.readValue(new File(filename),Player[].class);

        // Add each hero to the tree map and keep track of the greatest id
        for (Player player : playerArray) {
            players.put(player.getId(),player);
            if (player.getId() > nextId)
                nextId = player.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Player[] getPlayers() {
        synchronized(players) {
            return getPlayersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Player[] findPlayers(String containsText) {
        synchronized(players) {
            return getPlayersArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Player getPlayer(int id) {
        synchronized(players) {
            if (players.containsKey(id))
                return players.get(id);
            else
                return null;
        }
    }


}
