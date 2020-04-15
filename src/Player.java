public class Player {
    String name;
    int age;
    String team;
    String favfood;
    int powerRating;
    int contactRating;

    public Player (int a, String t, String FF, int PR, String n, int CR) {
        name = n;
        age = a;
        team = t;
        favfood = FF;
        powerRating = PR;
        contactRating = CR;
    }

    public Player () {
        name = randomName();
        age = randomInt(18,23);
        team = randomTeam();
        favfood = randomFood();
        powerRating = randomInt(60,100);
        contactRating = randomInt(60,100);
    }

    public String randomName() {
        String[] names = {"James", "Martin", "Nate", "Sirus", "Beyonce", "Alex", "Sasha"};
        return names[(int)(Math.random() * names.length)];
    }

    public String randomTeam(){
        String[] teams = {"Astronauts", "Red Socks", "Marlins", "Pirates", "Rangers"};
        return teams[(int)(Math.random() * teams.length)];
    }

    public String randomFood(){
        String[] foods = {"Tacos", "Burgers", "Spaghetti", "Daal", "Empandas", "Bagels"};
        return foods[(int)(Math.random() * foods.length)];
    }

    public int randomInt(int min, int max ){
        return ((int) (min + (Math.random() * (max-min))));
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Age: " + age + "\n" +
                "Team: " + team + "\n" +
                "Favorite Food: " + favfood + "\n" +
                "Contact: " + contactRating + "\n" +
                "Power: " + powerRating + "\n";
    }



}


// TO DO have an other class that extends JPanel
// this is to have an intro screen where u can choose the planel
// can include pictures that are not copyrighted as character models
// move the pick player method to this new class

