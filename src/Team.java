import java.awt.*;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileNotFoundException;

public class Team {
    //name of the file to retrieve data from
    String filePath;
    //name of the team (will be found in file)
    String teamName;
    //thee array for thee 9 players on thee team
    public Player[] lineup  = new Player[9];
    //this will keep track of where we are in the batting lineup
    int lineupPos;
    //the color of the text when the players/stats are displayed
    Color textColor;
    //checks for which team is being played with
    boolean humanPlayer;
//constructor asking for thee file name and the boolean asking if the human is playing or comp
    public Team(String filePath, boolean humanPlayer){
        //given the argument documents the fil path
        this.filePath = filePath;
        //input from argument being stored and initializing boolean variable
        this.humanPlayer = humanPlayer;
        //calls on initializeteam method to create the team based on thee file input
        initializeTeam(filePath);
        //starts at 0 since no one has batted
        lineupPos = 0;
    }

    public void initializeTeam(String filePath) {
        //TeamData is the text file name where the data for each team is
        //put players in the array here to fill the team
        //use input from a file
        //create scanner
        Scanner scanner = null;
        //read a specified file
        try {
            File file = new File(filePath);
            scanner = new Scanner(file);
        }
        //catch if the file isn't found
        catch (FileNotFoundException e){
            System.out.println("Error: File not found: " + filePath);
        }
        //keeps track fo what linenumber we are on for debugging purposes
        int lineNumber = 1;
        try {
            //reads the team name
            teamName = scanner.next();
            //goes through every character to make a space in the team name
            for (int i = 0; i < teamName.length(); i++) {
                if (teamName.charAt(i) == '_') teamName = teamName.substring(0,i) + " " + teamName.substring(i+1);
            }
            //adds to the linenumber since rest of the information is on subsequent lines
            lineNumber++;
            //reads the input for RGB color numbers to know what "color" the team is for the game
            int red = scanner.nextInt();
            int green = scanner.nextInt();
            int blue = scanner.nextInt();
            //using these inputs creates their color.
            textColor = new Color(red, green, blue);
        }
        //catching exception if the data does not exist/is in wrong type
        catch (InputMismatchException e){
            System.out.println("Error: could not read information from line: " + lineNumber);
        }
        //catching exception if there does not exist any data where we specify
        catch (NoSuchElementException f){
            System.out.println("There is no more data to read. Check your Team Data file"+
                    " and ensure it is complete");
        }
        //heree we will initialize every individual player inside the team
        for (int i = 0; i<lineup.length; i++){
            //give each of these values from a text file
            try {
                //reading inputs in order from the file
                String name = scanner.next();
                int age = scanner.nextInt();
                String team = this.teamName;
                int powerRating = scanner.nextInt();
                int contactRating = scanner.nextInt();
                //creates the player using the inputs read
                lineup[i] = new Player(age, team, powerRating, name, contactRating);
            }
            //the information does not fit into the correct variable (i.e trying to put a String in an int).
            catch (InputMismatchException e){
                System.out.println("Error: could not read information from line: " + lineNumber);
            }
            //the data was not found because it doesnt exist
            catch (NoSuchElementException f){
                System.out.println("There is no more data to read. Check your Team Data file"+
                        " and ensure it is complete");
            }
            //adding to the linenumber before initializing next player
            lineNumber++;
        }
    }
//toString method to display each player in a team
    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder(headerString() + "\n");
        int count = 1;
        for (Player p : lineup) {
            toReturn.append(count).append(": ").append(p).append("\n");
            count++;
        }
        return toReturn.toString();
    }
//this method initializes all of th players and their stats (member variables) to 0.
    //will be called when a new game is started
    public void resetPlayerStats() {
        for (int i = 0; i < lineup.length; i++) {
            lineup[i].atBats = 0;
            lineup[i].HRs = 0;
            lineup[i].singles = 0;
            lineup[i].doubles = 0;
            lineup[i].triples = 0;
            lineup[i].RBIs = 0;
        }
    }
//this method displays the titles for each of the things displayed on the loading screen
    public String headerString() {
        StringBuilder str = new StringBuilder("\t   ");
        str.append("Age");
        str.append(" ".repeat(5 - "Age".length()));
        str.append("Con");
        str.append(" ".repeat(5 - "Con".length()));
        str.append("Pow");
        str.append(" ".repeat(5 - "Pow".length()));
        return str.toString();
    }
}
