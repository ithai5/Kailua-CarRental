import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageCustomer {

    public static String newCustomerQuery() {
        String newCustomer = "INSERT INTO KeaProject.Customer (firstName, lastName, email)\nVALUES (";

        System.out.print("Please enter the customer's first name: ");
        String firstName = ScannerReader.scannerWords();
        System.out.println();
        newCustomer += QueryUtility.formatValue(firstName, true);

        System.out.print("Please enter the customer's last name: ");
        String lastName = ScannerReader.scannerWords();
        System.out.println();
        newCustomer += QueryUtility.formatValue(lastName, true);

        System.out.print("Which e-mail would the customer like to be contacted on?: ");
        String email = ScannerReader.scannerEMail();
        System.out.println();
        newCustomer += QueryUtility.formatValue(email, false) + ");";

        return newCustomer;
    }

    public static String updateCustomerQuery() throws SQLException {
        System.out.println("Which customer's details would you like to change?");
        printCustomerList("");
        int cusNum = ScannerReader.scannerInt(1, QueryUtility.tableSize("Customer", ""));
        int customerId = QueryUtility.chooseRowFromList(QueryUtility.getList("Customer", ""), cusNum);

        System.out.println("What information would you like to update?\n[1]. First name\n[2]. Last name\n[3]. E-mail address");
        int choice = ScannerReader.scannerInt(1, 3);
        String field = getField(choice);

        System.out.print("Please input the updated information: ");
        String changedInfo = "";
        if (choice == 3) {
            changedInfo = QueryUtility.formatValue(ScannerReader.scannerEMail(), false);
        } else {
            changedInfo = QueryUtility.formatValue(ScannerReader.scannerWords(), false);
        }
        System.out.println();

        String updateQuery = "UPDATE Customer\nSET " + field + " = " + changedInfo + "\nWHERE Customer.customer_id = " + customerId + ";";
        return updateQuery;
    }

    public static void printCustomerList(String filter) throws SQLException {
        ResultSet listToPrint = QueryUtility.getList("Customer", filter);
        int i = 0;
        while(listToPrint.next()) {
            String listNum = "[" + ++i + "].";
            String fName = listToPrint.getString(2);
            String lName = listToPrint.getString(3);
            String email = listToPrint.getString(4);
            System.out.printf("%-7s%-10s%-10s%-10s", listNum, fName, lName, email);
            System.out.println();
        }
    }

    public static String deleteCustomerQuery() throws SQLException {
        System.out.println("Which customer would you like to remove from the database?");
        printCustomerList("");
        int cusNum = ScannerReader.scannerInt(1, QueryUtility.tableSize("Customer", ""));
        int customerId = QueryUtility.chooseRowFromList(QueryUtility.getList("Customer", ""), cusNum);

        String deleteQuery = "DELETE FROM Customer WHERE Customer.customer_id = " + customerId;
        return deleteQuery;
    }

    public static int findCustomerId() throws SQLException {
        System.out.println("What information would you like to search by?\n[1]. First name\n[2]. Last name\n[3]. E-mail address");
        String cusField = ManageCustomer.getField(ScannerReader.scannerInt(1, 3));
        System.out.print("Search: ");
        String cusParam = ScannerReader.scannerWords();

        String searchHit = QueryUtility.findPrimaryKey("Customer", cusField, cusParam);
        if (searchHit.isEmpty()) {
            return -1;
        }
        return QueryUtility.extractIntFromString(searchHit);
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

    public static int assignCusId() throws SQLException {
        int cusId;
        do {
            cusId = ManageCustomer.findCustomerId();
        } while (cusId == -1);
        return cusId;
    }

}
