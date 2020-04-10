import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class QueryUtility {
    //Consider merging this with DBInteraction

    //Method for surrounding string tokens by quotation marks.
    //If withComma = true, a comma and a space will be added
    //to the end of the token.
    public static String formatValue(String value, boolean withComma) {
        if (withComma) {
            return "\"" + value + "\", ";
        } else {
            return "\"" + value + "\"";
        }
    }

    //A method that counts the total number of rows for a table.
    //Making use of the filter parameter, it can show the number of hits for
    //a specific search instead.
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

    //A method used to retrieve the primary key from a table, with whatever
    //WHERE clause is passed in through the fieldName and searchParam parameters.
    public static String findPrimaryKey(String table, String fieldName, String searchParam) throws SQLException {
        String queryId = "SELECT *\nFROM " + table + "\nWHERE " + fieldName + " = " + searchParam + ";";
        ResultSet rs = DBInteraction.getData(queryId);

        rs.next();
        return rs.getString(1);
    }

    //Pass in a ResultSet representing the list you asked the user to choose from,
    //and pass in the number they chose
    public static int chooseRowFromList(ResultSet list, int rowNum) throws SQLException {
        list.absolute(rowNum);
        return list.getInt(1);
    }


    public static int extractIntFromString(String token) {
        Scanner extract = new Scanner(token);

        return extract.nextInt();
    }

    //Pass whatever goes past the WHERE part of your query into 'filter', if any.
    //If you have no filters, pass in ""
    //Please note, there's automatically a space between WHERE and the filter, and
    //a ";" after the filter
    public static ResultSet getList(String table, String filter) {
        String selectQuery = "SELECT * FROM " + table;

        if (!filter.isEmpty()) {
            selectQuery += "\nWHERE " + filter;
        } else {
            selectQuery += ";";
        }

        return DBInteraction.getData(selectQuery);
    }
}
