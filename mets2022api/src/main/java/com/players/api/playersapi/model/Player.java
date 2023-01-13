package com.players.api.playersapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Player entity
 */
public class Player {
    private static final Logger LOG = Logger.getLogger(Player.class.getName());

    @JsonProperty("id") private int Id;
    @JsonProperty("name") private String Name;
    @JsonProperty("Dob") private String Dob;
    @JsonProperty("Bats") private String Bats;
    @JsonProperty("Throws") private String Throws;
    @JsonProperty("Height") private String Height;
    @JsonProperty("Weight") private String Weight;
    @JsonProperty("Position") private String Position;
    @JsonProperty("Stats") private String Stats;
    @JsonProperty("Picture") private String Picture;
    @JsonProperty("Video") private String Video;


    /**
     * Create a player with the given entries
     * @param id The id of the player
     * @param name The name of the player
     * @param Dob The Date of Birth of the player
     * @param Bats The way the player bats
     * @param Throws The way the player throws
     * @param Height The height of the player
     * @param Weight The weight of the player
     * @param Position The position of the player
     * @param Stats The stats of the player
     * @param Picture The picture of the player
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Player(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("Dob") String dob, @JsonProperty("Bats") String bats, 
    @JsonProperty("Throws") String Throws, @JsonProperty("Height") String height, @JsonProperty("Weight") String weight, @JsonProperty("Position") String position,
    @JsonProperty("Stats") String stats, @JsonProperty("Picture") String picture) {
        this.Id = id;
        this.Name = name;
        this.Dob = dob;
        this.Bats = bats;
        this.Throws = Throws;
        this.Height = height;
        this.Weight = weight;
        this.Position = position;
        this.Stats = stats;
        this.Picture = picture;
    }

    /**
     * Retrieves the Id of the player
     * @return The id of the player
     */
    public int getId() {return Id;}


    /**
     * Retrieves the name of the hero
     * @return The name of the hero
     */
    public String getName() {return Name;}

}