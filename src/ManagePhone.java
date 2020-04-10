import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagePhone {

    public static String newPhoneQuery(int cusId) {

        String newPhone = "INSERT INTO KeaProject.Phone (phoneNumber, phoneType, customer_id)\nVALUES (";
        System.out.print("Please enter the customer's telephone number: ");
        String phoneNum = QueryUtility.formatValue(ScannerReader.scannerIntAsString(8), true);
        newPhone += phoneNum;
        System.out.println();

        System.out.println("Is this number a personal phone?");
        String phoneType = "";
        if (ScannerReader.scannerBoolean(3)) {
            phoneType = QueryUtility.formatValue("MobilePhone", true);
        } else {
            phoneType = QueryUtility.formatValue("HomePhone", true);
        }
        newPhone += phoneType;

        newPhone += cusId + " );";

        return newPhone;
    }

    public static String updatePhoneQuery() throws SQLException {
        System.out.println("Who owns the phone?");
        String filter = "Phone.customer_id = " + ManageCustomer.findCustomerId();

        int numberOfPhoneNums = QueryUtility.tableSize("Phone", filter);
        int phoneId = getPhoneFromList(numberOfPhoneNums, filter);

        System.out.println("What information would you like to update?\n[1]. Phone Number\n[2]. Phone Type");
        int choice = ScannerReader.scannerInt(1, 2);
        String field = getField(choice);

        System.out.print("Please input the updated information: ");
        String changedInfo = "";
        if (choice == 1) {
            changedInfo = QueryUtility.formatValue(ScannerReader.scannerIntAsString(), false);
        } else {
            changedInfo = QueryUtility.formatValue(ScannerReader.scannerWords(), false);
        }
        System.out.println();

        return "UPDATE Phone\nSET " + field + " = " + changedInfo + "\nWHERE Phone.phone_id = " + phoneId + ";";
    }

    //Leave no Orphans!!
    public static String deletePhoneQuery() throws SQLException {
        System.out.println("Who owns the phone?");
        String filter = "Phone.customer_id = " + ManageCustomer.findCustomerId();
        printPhoneList(filter);

        int numberOfPhoneNums = QueryUtility.tableSize("Phone", filter);
        int phoneId = getPhoneFromList(numberOfPhoneNums, filter);

        String deleteQuery = "DELETE FROM Phone WHERE Phone.phone_id = " + phoneId + ";";
        return deleteQuery;
    }

    //0 = phone_id, 1 = phoneNumber, 2 = phoneType and 3 = customer_id
    public static String getField(int fieldName) {
        switch (fieldName) {
            case 0:
                return "Phone.phone_id";
            case 1:
                return "Phone.phoneNumber";
            case 2:
                return "Phone.phoneType";
            case 3:
                return "Phone.customer_id";
            default:
                return "gvrbvnzo";
        }
    }

    public static void printPhoneList(String filter) throws SQLException {
        ResultSet listToPrint = QueryUtility.getList("Phone", filter);
        int i = 0;
        while (listToPrint.next()) {
            String listNum = "[" + ++i + "].";
            String phoneNumber = listToPrint.getString(2);
            String phoneType = listToPrint.getString(3);
            int cusId = listToPrint.getInt(4);
            System.out.printf("%-7s%-10s%-10s%5d", listNum, phoneNumber, phoneType, cusId);
            System.out.println();
        }
    }
    
    public static int findPhoneId() throws SQLException {
        System.out.println("What information would you like to search by?\n[1]. Phone Number\n[2]. Phone Type");
        String phoneField = ManagePhone.getField(ScannerReader.scannerInt(1,2));
        System.out.print("Search: ");
        String phoneParam = ScannerReader.scannerWords();

        return QueryUtility.extractIntFromString(QueryUtility.findPrimaryKey("Phone", phoneField, phoneParam));
    }

    public static int getPhoneFromList(int totalPhones, String filter) throws SQLException {
        int phoneInList;

        if (totalPhones > 1) {
            System.out.println("Please choose which number:");
            printPhoneList(filter);
            phoneInList = ScannerReader.scannerInt(1, totalPhones);
        } else {
            phoneInList = 1;
        }
        return QueryUtility.chooseRowFromList(QueryUtility.getList("Phone", filter), phoneInList);
    }
}
