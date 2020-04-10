import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        Scanner sc = new Scanner(new File("password.txt"));
        new DBInteraction(sc.next(), sc.next(), sc.next());
        Menu.mainMenu();
    }
}
