import java.awt.*;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileNotFoundException;

public class Team {
    String filePath;
    String teamName;
    public Player[] lineup  = new Player[9];
    int lineupPos;
    Color textColor;
    final boolean humanPlayer;

    public Team(String filePath, boolean humanPlayer){
        this.filePath = filePath;
        this.humanPlayer = humanPlayer;
        initializeTeam(filePath);
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
        catch (FileNotFoundException e){
            System.out.println("Error: File not found: " + filePath);
        }

        int lineNumber = 1;
        try {
            teamName = scanner.next();
            for (int i = 0; i < teamName.length(); i++) {
                if (teamName.charAt(i) == '_') teamName = teamName.substring(0,i) + " " + teamName.substring(i+1);
            }
            lineNumber++;
            int red = scanner.nextInt();
            int green = scanner.nextInt();
            int blue = scanner.nextInt();
            textColor = new Color(red, green, blue);
        }
        catch (InputMismatchException e){
            System.out.println("Error: could not read information from line: " + lineNumber);
        }
        catch (NoSuchElementException f){
            System.out.println("There is no more data to read. Check your Team Data file"+
                    " and ensure it is complete");
        }
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
        StringBuilder toReturn = new StringBuilder(headerString() + "\n");
        int count = 1;
        for (Player p : lineup) {
            toReturn.append(count).append(": ").append(p).append("\n");
            count++;
        }
        return toReturn.toString();
    }

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
