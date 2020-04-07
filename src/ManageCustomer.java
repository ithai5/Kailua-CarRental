import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageCustomer {
    public ManageCustomer() {
    }

    public static String quoteForString(String value, boolean withComma) {
        return withComma ? "\"" + value + "\", " : "\"" + value + "\"";
    }

    public static String newCustomerQuery() {
        System.out.print("Please enter the customer's first name: ");
        String firstName = ScannerReader.scannerWords();
        System.out.println();
        System.out.print("Please enter the customer's last name: ");
        String lastName = ScannerReader.scannerWords();
        System.out.println();
        System.out.print("Which e-mail would the customer like to be contacted on?: ");
        String email = ScannerReader.scannerEMail();
        System.out.println();
        String var10000 = quoteForString(firstName, true);
        String newCustomer = "INSERT INTO KeaProject.Customer (firstName, lastName, email)\nVALUES (" + var10000 + quoteForString(lastName, true) + quoteForString(email, false) + ");";
        return newCustomer;
    }

    public static String updateCustomerQuery() throws SQLException {
        System.out.println("Which customer's details would you like to change?");
        int customerId = getCustomerById();
        System.out.println("What information would you like to update?\n[1]. First name\n[2]. Last name\n[3]. E-mail address");
        int choice = ScannerReader.scannerInt(1, 3);
        String field = getField(choice);
        System.out.print("Please input the updated information: ");
        String changedInfo = "";
        if (choice == 3) {
            changedInfo = quoteForString(ScannerReader.scannerEMail(), false);
        } else {
            changedInfo = quoteForString(ScannerReader.scannerWords(), false);
        }

        System.out.println();
        String updateQuery = "UPDATE Customer\nSET " + field + " = " + changedInfo + "\nWHERE Customer.customer_id = " + customerId + ";";
        return updateQuery;
    }

    public static void printCustomerList() throws SQLException {
        ResultSet listToPrint = getCustomerList();
        int i = 0;
        while(listToPrint.next()) {
            String cusId = "[" + ++i + "].";
            String fName = listToPrint.getString(2);
            String lName = listToPrint.getString(3);
            System.out.printf("%-5s%-10s%-10s", cusId, fName, lName);
            System.out.println();
        }
    }

    public static String deleteCustomerQuery() throws SQLException {
        System.out.println("Which customer would you like to remove from the database?");
        int customerId = getCustomerById();
        String deleteQuery = "DELETE FROM Customer WHERE Customer.customer_id = " + customerId;
        return deleteQuery;
    }

    public static int findCustomerId(int fieldName, String searchParam) throws SQLException {
        String field = getField(fieldName);
        String queryId = "SELECT Customer.customer_id\nFROM Customer\nWHERE " + field + " = \"" + searchParam + "\";";
        ResultSet rs = DBInteraction.getData(queryId);

        rs.next();
        return rs.getInt(1);
    }

    public static int getCustomerById() throws SQLException {
        printCustomerList();
        int cusNumber = ScannerReader.scannerInt(1, tableSize("Customer"));
        ResultSet cusList = getCustomerList();

        for (int i = 0; i < cusNumber; ++i) {
            cusList.next();
        }

        return cusList.getInt(1);
    }

    public static String getField(int fieldName) {
        switch(fieldName) {
            case 0:
                return "Customer.customer_id";
            case 1:
                return "Customer.firstName";
            case 2:
                return "Customer.lastName";
            case 3:
                return "Customer.email";
            default:
                return "gvrbvnzo";
        }
    }

    public static int tableSize(String table) throws SQLException {
        ResultSet size = DBInteraction.getData("SELECT COUNT(*)\nFROM " + table);
        size.next();
        return size.getInt(1);
    }

    public static ResultSet getCustomerList() {
        return DBInteraction.getData("SELECT Customer.customer_id, Customer.firstName, Customer.lastName FROM Customer");
    }
}
