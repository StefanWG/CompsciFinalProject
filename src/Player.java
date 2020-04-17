public class Player {
    String name;
    int age;
    String team;
    int powerRating;
    int contactRating;
    int atBats;
    int Singles;
    int Doubles;
    int Triples;
    int HRs;

    public Player (int a, String t, int PR, String n, int CR) {
        name = n;
        age = a;
        team = t;
        powerRating = PR;
        contactRating = CR;
        atBats = 0;
        Singles = 0;
        Doubles = 0;
        Triples = 0;
        HRs = 0;
    }

    public Player (String teamName) {
        name = randomName();
        age = randomInt(18,40);
        team = teamName;
        powerRating = randomInt(60,100);
        contactRating = randomInt(60,100);
        atBats = 0;
        Singles = 0;
        Doubles = 0;
        Triples = 0;
        HRs = 0;
    }

    public String randomName() {
        String[] names = {"James", "Martin", "Nate", "Sirus", "Beyonce", "Alex", "Sasha"};
        return names[(int)(Math.random() * names.length)];
    }

    public String randomTeam(){
        String[] teams = {"Astronauts", "Red Socks", "Marlins", "Pirates", "Rangers"};
        return teams[(int)(Math.random() * teams.length)];
    }

    public int randomInt(int min, int max ){
        return ((int) (min + (Math.random() * (max-min))));
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Age: " + age + "\n" +
                "Team: " + team + "\n" +
                "Contact: " + contactRating + "\n" +
                "Power: " + powerRating + "\n";
    }



}


// TO DO have an other class that extends JPanel
// this is to have an intro screen where u can choose the planel
// can include pictures that are not copyrighted as character models
// move the pick player method to this new class

