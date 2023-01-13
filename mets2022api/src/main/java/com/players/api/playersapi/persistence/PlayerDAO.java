package com.players.api.playersapi.persistence;

import java.io.IOException;

import com.players.api.playersapi.model.Player;

/**
 * Defines the interface for Player object persistence
 */
public interface PlayerDAO {
    /**
     * Retrieves all {@linkplain Player players}
     * 
     * @return An array of {@link Player player} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Player[] getPlayers() throws IOException;

    /**
     * Finds all {@linkplain Player players} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Player players} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Player[] findPlayers(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Player player} with the given id
     * 
     * @param id The id of the {@link Player player} to get
     * 
     * @return a {@link Player player} object with the matching id
     * <br>
     * null if no {@link Player player} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Player getPlayer(int id) throws IOException;
}
