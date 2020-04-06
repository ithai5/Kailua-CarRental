import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void customerMenu() throws SQLException {
        System.out.println("Please choose an option\n[1]. Create new customer\n[2]. View all customers\n[3]. Update customer info\n[4]. Delete customer");
        int choice = ScannerReader.scannerInt(1, 4);
        switch(choice) {
            case 1:
                DBInteraction.updateDatabase(ManageCustomer.newCustomerQuery());
                System.out.println("New customer has been created!");
                break;
            case 2:
                ManageCustomer.printCustomerList();
                break;
            case 3:
                DBInteraction.updateDatabase(ManageCustomer.updateCustomerQuery());
                break;
            case 4:
                DBInteraction.updateDatabase(ManageCustomer.deleteCustomerQuery());
        }

    }

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        System.out.println("hello world");
        Scanner sc = new Scanner(new File("password.txt"));
        new DBInteraction(sc.next(), sc.next(), sc.next());
        customerMenu();
    }
}
