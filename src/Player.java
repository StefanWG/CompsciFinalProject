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
        return "Player{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", team='" + team + '\'' +
                ", favfood='" + favfood + '\'' +
                ", powerRating=" + powerRating +
                ", contactRating=" + contactRating +
                '}';
    }



}


// TO DO have an other class that extends JPanel
// this is to have an intro screen where u can choose the planel

