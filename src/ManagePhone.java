import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagePhone {

    //Pass whatever goes past the WHERE part of your query into 'filter', if any.
    //If you have no filters, pass in ""
    //Please note, there's automatically a space between WHERE and the filter, and
    //a ";" after the filter
    public static ResultSet getPhoneList(String filter) {
        String selectQuery = "SELECT * FROM KeaProject.Phone";
        if (!filter.isEmpty()) {
            selectQuery += "\nWHERE " + filter;
        }

        selectQuery += ";";
        return DBInteraction.getData(selectQuery);
    }

    public static String newPhoneQuery(int cusId) {

        String newPhone = "INSERT INTO KeaProject.Phone (phoneNumber, phoneType, customer_id)\nVALUES (";
        System.out.print("Please enter the customer's telephone number: ");
        String phoneNum = ManageCustomer.quoteForString(ScannerReader.scannerIntAsString(8), true);
        newPhone += phoneNum;
        System.out.println();

        System.out.println("Is this number a personal phone?");
        String phoneType = "";
        if (ScannerReader.scannerBoolean(3)) {
            phoneType = ManageCustomer.quoteForString("MobilePhone", true);
        } else {
            phoneType = ManageCustomer.quoteForString("HomePhone", true);
        }
        newPhone += phoneType;

        newPhone += cusId + " );";

        return newPhone;
    }

    public static String updatePhoneQuery(int cusId) throws SQLException {
        String filter = "Phone.customer_id = " + cusId;
        int numberOfPhoneNums = tableSize("Phone", filter);

        int phoneId;
        if (numberOfPhoneNums > 1) {
            System.out.println("Please choose which number's details you would like to edit:");
            printPhoneList(filter);
            int choice = ScannerReader.scannerInt(1, numberOfPhoneNums);
            phoneId = getPhoneById(choice);
        } else {
            phoneId = getPhoneById(1);
        }

        System.out.println("What information would you like to update?\n[1]. Phone Number\n[2]. Phone Type");
        int choice = ScannerReader.scannerInt(1, 2);
        String field = getField(choice);
        System.out.print("Please input the updated information: ");
        String changedInfo = "";
        if (choice == 1) {
            changedInfo = ManageCustomer.quoteForString(ScannerReader.scannerIntAsString(), false);
        } else {
            changedInfo = ManageCustomer.quoteForString(ScannerReader.scannerWords(), false);
        }
        System.out.println();

        return "UPDATE Phone\nSET " + field + " = " + changedInfo + "\nWHERE Phone.phone_id = " + phoneId + ";";
    }

    //Leave no Orphans!!
    public static String deletePhoneQuery(int cusId) throws SQLException {
        String filter = "Phone.customer_id = " + cusId;
        printPhoneList(filter);
        int numberOfPhoneNums = tableSize("Phone", filter);
        int phoneId;
        if (numberOfPhoneNums > 1) {
            System.out.println("Please choose which number you would like to delete:");
            printPhoneList(filter);
            int choice = ScannerReader.scannerInt(1, numberOfPhoneNums);
            phoneId = getPhoneById(choice);
        } else {
            phoneId = getPhoneById(1);
        }

        String deleteQuery = "DELETE FROM Phone WHERE " + filter;
        return deleteQuery;
    }


    //0 = phone_id, 1 = phoneNumber, 2 = phoneType and 3 = customer_id
    public static String getField(int fieldName) {
        switch(fieldName) {
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
        ResultSet listToPrint = getPhoneList(filter);
        int i = 0;
        while(listToPrint.next()) {
            String listNum = "[" + ++i + "].";
            String phoneNumber = listToPrint.getString(2);
            String phoneType = listToPrint.getString(3);
            int cusId = listToPrint.getInt(4);
            System.out.printf("%-7s%-10s%-10s%5d", listNum, phoneNumber, phoneType, cusId);
            System.out.println();
        }
    }

    public static int getPhoneById(int listNum) throws SQLException {
        ResultSet phoneList = getPhoneList("");
        phoneList.absolute(listNum);

        return phoneList.getInt(1);
    }

    public static int findPhoneId(int fieldName, String searchParam) throws SQLException {
        String field = getField(fieldName);
        String queryId = "SELECT Phone.phone_id\nFROM Phone\nWHERE " + field + " = \"" + searchParam + "\";";
        ResultSet rs = DBInteraction.getData(queryId);

        rs.next();
        return rs.getInt(1);
    }

    //Pass whatever goes past the WHERE part of your query into 'filter', if any.
    //If you have no filters, pass in ""
    //Please note, there's automatically a space between WHERE and the filter, and
    //a ";" after the filter
    public static int tableSize(String table, String filter) throws SQLException {
        String countQuery = "SELECT COUNT(*)\nFROM " + table;
        if (!filter.isEmpty()) {
            countQuery += "\nWHERE " + filter;
        }

        countQuery += ";";
        ResultSet size = DBInteraction.getData(countQuery);
        size.next();
        return size.getInt(1);
    }
}
