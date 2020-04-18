import java.awt.*;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileNotFoundException;

public class Team {
    String teamName;
    //9 player team
    public Player[] lineup;
    //will keep track of where in the lineup we are
    int lineupPos;
    //home team (our team)
    Color textColor;

    public Team(String name, String fileName, Color color){
        teamName = name;
        lineup = new Player[9];
        initializeTeam(fileName);
        lineupPos = 0;
        textColor = color;
    }
    //away team (randomized)
    public Team(){
        lineupPos=0;
        lineup = new Player[9];
        //initialize the team with all random players
        initializeRandTeam();
        teamName = randTeamName();
        textColor = new Color(0,102,0);
    }

    public void initializeRandTeam(){
        for(int i = 0; i<lineup.length; i++){
            lineup[i] = new Player(teamName);
        }
    }

    public String randTeamName(){
        String[] names = {"Hornets", "Hawks", "Mustangs", "Rams", "Lions", "Cowboys"};
        return names[(int)(Math.random() * names.length)];
    }

    public void initializeTeam(String TeamData) {
        //TeamData is the text file name where the data for each team is
        //put players in the array here to fill the team
        //use input from a file
        //create scanner
        Scanner scanner = null;
        //read a specified file
        try {
            scanner = new Scanner(new File(TeamData));
        }
        catch (FileNotFoundException e){
            System.out.println("Error: File not found: " + TeamData);
        }

        int lineNumber = 1;
        for (int i = 0; i<lineup.length; i++){
            //give each of these values from a text file
            try {
                String name = scanner.next();
                int age = scanner.nextInt();
                String team = this.teamName;
                int powerRating = scanner.nextInt();
                int contactRating = scanner.nextInt();
                lineup[i] = new Player(age, team, powerRating, name, contactRating);
            }
            catch (InputMismatchException e){
                System.out.println("Error: could not read information from line: " + lineNumber);
            }
            catch (NoSuchElementException f){
                System.out.println("There is no more data to read. Check your Team Data file"+
                        " and ensure it is complete");
            }
            lineNumber++;
        }
    }

    @Override
    public String toString() {
        String toReturn = headerString() + "\n";
        int count = 1;
        for (Player p : lineup) {
            toReturn += count + ": " + p + "\n";
            count++;
        }
        return toReturn;
    }

    public String headerString() {
        String str = "\t   ";
        str += "Age";
        for (int i = 0; i < 5 - "Age".length(); i++) {
            str += " ";
        }
        str += "Con";
        for (int i = 0; i < 5 - "Con".length(); i++) {
            str += " ";
        }
        str += "Pow";
        for (int i = 0; i < 5 - "Pow".length(); i++) {
            str += " ";
        }
        return str;
    }
}
