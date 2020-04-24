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
    int RBIs;

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
        RBIs= 0;
    }



    public void addRBIs(int n){
        RBIs = RBIs + n;
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


// TO DO have an other class that extends JPanel
// this is to have an intro screen where u can choose the planel
// can include pictures that are not copyrighted as character models
// move the pick player method to this new class

