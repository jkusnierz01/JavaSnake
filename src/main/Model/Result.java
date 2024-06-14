package main.Model;

import java.io.*;

/**
 * The Result class represents a player's result in the game.
 * It includes methods to get and set the result and player's name, 
 * and to save the result to a file.
 */
public class Result {
    /**
     * The result of the player.
     */
    private int result;

    /**
     * The name of the player.
     */
    private String name;

    /**
     * Constructs a Result object with the specified result and name.
     * 
     * @param result the result of the player
     * @param name the name of the player
     */
    public Result(int result, String name) {
        this.result = result;
        this.name = name;
    }

    /**
     * Default constructor.
     */
    public Result() { }

    /**
     * Returns the result of the player.
     * 
     * @return the result of the player
     */
    public int getResult() {
        return result;
    }

    /**
     * Sets the result of the player.
     * 
     * @param result the new result of the player
     */
    public void setResult(int result) {
        this.result = result;
    }

    /**
     * Returns the name of the player.
     * 
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     * 
     * @param name the new name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Saves the result and the name of the player to a file.
     * 
     * @throws IOException if an I/O error occurs
     */
    public void saveResult() throws IOException {
        var output = new BufferedWriter(new FileWriter("src/results/result.txt", true));
        output.append(name + " " + result + "\n");
        output.close();
    }
}
