/**
 * The player class stores all the necessary information about each individual player
 * in the game. A team is made up of 9 distinct players.
 **/

public class Player {
    //these variable names are exactly what they sound like
    //each store data about the specific player
    String name;
    int age;
    String team;
    int powerRating;
    int contactRating;
    int atBats;
    int singles;
    int doubles;
    int triples;
    int HRs;
    int RBIs;

    public Player (int a, String t, int PR, String n, int CR) {
        name = n;
        age = a;
        team = t;
        powerRating = PR;
        contactRating = CR;
        atBats = 0;
        singles = 0;
        doubles = 0;
        triples = 0;
        HRs = 0;
        RBIs= 0;
    }

    @Override
    public String toString() {
        String str = "";
        str += name + "\t   ";
        str += age + "    ";
        str += contactRating + "    ";
        str += powerRating;
        return str;
    }
}
