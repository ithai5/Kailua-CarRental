//Driver info class from Nesrin

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverInfo {


        String query = DriverInfoMenu();

        public static ResultSet getQueryResultSet (String query) {
            // call the DB and get results
            return DBInteraction.getData(query);
        }

    //---------------------------------------------------------------
    //MENU METHODS
    //---------------------------------------------------------------


    public static String DriverInfoMenu(){

        System.out.println("What would you like to do \n" +
                "[1]. View All Drivers \n" +
                "[2]. Search Specific Drivers \n" +
                "[3]. Edit Existing Driver Information \n" +
                "[4]. Create New Driver \n" +
                "[0]. Return to the menu");
        int userInput = ScannerReader.scannerInt(0,4);
        String query = null;
        switch (userInput){
            case 1:
                seeDrivers();
                break;
            case 2:
                query = searchSpecificDriver();
                break;
            case 3:
                EditSpecificDriver();
                break;
            case 4:
                createQueryForAddingDriver();
                break;
            case 0:
                System.out.println("return to the menu");
                return null;
            default:
                System.out.println("something went wrong");
                return null;
        }
        return query;

    }


    public static void EditSpecificDriver() { //turned into void

        //String query = searchSpecificDriver();

        System.out.println("What would you like to do \n" +
                "[1]. Update Specific Driver \n" +
                "[2]. Delete Specific Driver \n" +
                "[0]. Return to the menu");
        int userInput = ScannerReader.scannerInt(0,2);
        //String query = null;
        switch (userInput){
            case 1:
                updateDriver(); //will no longer use the query. Using driver's licence instead. If the query returns with a name, it will not produce unique values
                break;
            case 2:
                deleteDriver(); //will no longer use the query. Using driver's licence instead. If the query returns with a name, it will not produce unique values
                break;
            case 0:
                System.out.println("return to the menu");
                //break;
                return;
            default:
                System.out.println("something went wrong");
        }
        //return query;

    }


    //---------------------------------------------------------------
    //DELETE, ADD, UPDATE DRIVER METHODS
    //---------------------------------------------------------------


    public static void deleteDriver () //double check value
    {

        System.out.println("Please type driver's licence number of the driver you wish to delete");
        int driverLicenceNumber = ScannerReader.scannerInt();

        String query = "DELETE FROM KeaProject.DriverInfo WHERE driverLicenceNumber = " + driverLicenceNumber;
        int result = DBInteraction.updateDatabase(query);
        if (result>0){
            System.out.println ("Delete successful");
        }else{
            System.out.println("Error Occurred");
        }

        //return "DELETE FROM KeaProject.DriverInfo WHERE driverLicenceNumber = " + driverLicenceNumber;
    }


    public static void updateDriver () //got rid of return queries
    {
        System.out.println("Please type driver's licence number of the driver you wish to update");
        int licence = ScannerReader.scannerInt();
        String query = null;
        System.out.println("what would you like to change:\n" +
                "[1]. Licence Number\n"+
                "[2]. First Name\n"+
                "[3]. Last Name \n"+
                "[4]. Driver Since Date\n"+
                "[5]. Customer ID\n"+
                "[0]. Quit");

        int userInput = ScannerReader.scannerInt(0, 5);
        String newValue;

        switch (userInput) {
            case 0:
                return;
            case 1:
                System.out.println("Please type the new licence number ");
                int newNumber = ScannerReader.scannerInt();
                query = "UPDATE  KeaProject.DriverInfo SET driverLicenceNumber = '" + newNumber + "' WHERE DriverLicenceNumber = " + licence;
                break;
            case 2:
                System.out.println("Please type the new first name");
                newValue = ScannerReader.scannerWords();
                query = "UPDATE  KeaProject.DriverInfo SET driverFirstName = '" + newValue + "' WHERE DriverLicenceNumber = " + licence;
                break;
            case 3:
                System.out.println("Please type the new last name");
                newValue = ScannerReader.scannerWords();
                query = "UPDATE  KeaProject.DriverInfo SET driverLastName = '" + newValue + "' WHERE DriverLicenceNumber = " + licence;
                break;
            case 4:
                System.out.println("Please type the Driver Since date you wish to change to. Please type date in YYYY-MM-DD format");
                newValue = ScannerReader.scannerAll();
                query = "UPDATE  KeaProject.DriverInfo SET driverSinceDate = '" + newValue + "' WHERE DriverLicenceNumber = " + licence;
                break;
            case 5:
                System.out.println("Please type the customer id you wish to change to");
                int newId = ScannerReader.scannerInt();
                query = "UPDATE  KeaProject.DriverInfo SET customer_id = '" + newId + "' WHERE DriverLicenceNumber = " + licence;
                break;
            default: //break;

                return;

        }
        DBInteraction.updateDatabase(query);

    }

        // collecting the information from the user and return a query for the DB.
        public static void createQueryForAddingDriver () { //changed to void


            System.out.println("To add a driver, please input the corresponding customer_id");
            //below code verifies that the user inputs a value from existing customers

            try {
                String selectQuery = "SELECT customer_id FROM KeaProject.Customer";
                String customer_id = ScannerReader.scannerAll();

                String whereQuery = " WHERE customer_id = " + customer_id;
                String q1 = selectQuery + whereQuery;
                ResultSet result = DBInteraction.getData(q1);

                if (result.next()){
                    System.out.println("Customer_id found");

                System.out.println("Please type driver's licence number");
               // int driverLicenceNumber = ScannerReader.scannerInt();
                long driverLicenceNumber = scannerLong();
                System.out.println("please type first name");
                String driverFirstName = ScannerReader.scannerWords();
                System.out.println("Please type last name");
                String driverLastName = ScannerReader.scannerWords();
                System.out.println("Please type driver since date. Please type date in YYYY-MM-DD format");
                String driverSinceDate = ScannerReader.scannerAll();

                String query = " VALUES (" + driverLicenceNumber + ", '" + driverFirstName + "', '" + driverLastName + "', '" + driverSinceDate + "', " + customer_id + ")";
                String completeQuery = "INSERT INTO KeaProject.DriverInfo (driverLicenceNumber, driverFirstName, driverLastName, driverSinceDate, customer_id)" + query;
                System.out.println("Driver is now created.");
                DBInteraction.updateDatabase(completeQuery);


                }else{
                    System.out.println("Customer_id not found. Please make a customer first before you add drivers onto the ID");
                }
            }catch (Exception e) {
                System.out.println("error" + e.getMessage());
            }
         }


    //---------------------------------------------------------------
    //SEARCHING METHODS IN MENU
    //---------------------------------------------------------------


        //show all the drivers
        public static void seeDrivers()
        {
            String query = "SELECT * FROM KeaProject.DriverInfo"; //create a query to the DB
            //DBInteraction.getData(query); not necessary because the DBInteraction in called in the below method
            showResultSetOfaDriver(query);

        }

        public static String searchSpecificDriver () { //DBInteraction is present in the createSearchQuery
            String field = searchField(); //asks user what column they want to search within
            String query = createSearchQuery(field); //provides WHERE value
            return query;
        }




    //---------------------------------------------------------------
    //SETTING UP QUERIES FOR ABOVE SEARCHING METHODS
    //---------------------------------------------------------------


        //returns a table with all the information about one driver from the driver table
        public static void showResultSetOfaDriver (String query)
        {

            ResultSet rs = DBInteraction.getData(query);

            System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "driverLicenceNumber", "driverFirstName", "driverLastName", "DriverSinceDate", "customer_id");
            System.out.println("________________________________________________________________________________________________________________");
            try {
                while (rs.next()) {
                    System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", rs.getLong("driverLicenceNumber"), rs.getString("driverFirstName"),
                                    rs.getString("driverLastName"), rs.getString("driverSinceDate"), rs.getString("customer_id")); //make sure to use Thomas's class
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    //method to find which search field the user would like to search in the Driver table
        public static String searchField () {

            //user will choose the field to search in
            System.out.println("What would you like to search by?\n" +
                    "[1]. First Name\n" +
                    "[2]. Last Name\n" +
                    "[3]. Drivers Licence\n" +
                    "[4]. Driver Since Date\n" +
                    "[5]. Customer number\n");
            int userInput = ScannerReader.scannerInt(1, 5);
            switch (userInput) {
                case 1:
                    return "driverFirstName";
                case 2:
                    return "driverLastName";
                case 3:
                    return "driverLicenceNumber";
                case 4:
                    return "driverSinceDate";
                case 5:
                    return "customer_id";
                default:
                    System.out.println("something went wrong");
                    return null;
            }
        }


        //create search query and print out the results. Make this clearer?
        public static String createSearchQuery (String searchField)
        {
           // USE KeaProject.driverInfo SELECT * FROM KeaProject.DriverInfo;
            //double-check statement
            String selectQuery = "SELECT driverLicenceNumber, driverFirstName, driverLastName, driverSinceDate, customer_id FROM KeaProject.DriverInfo";
            System.out.println("Search: ");
            String userInput = ScannerReader.scannerAll();
            String whereQuery = " WHERE " + searchField + "= '" + userInput + "'";
            String query = selectQuery + whereQuery;
            showResultSetOfaDriver(query);
            DBInteraction.getData(query); //double-check that this is needed here
            return query;
        }


    public static long scannerLong ()
    { //Used when needing int input
        Scanner console = new Scanner(System.in);
        long num = 0;
        boolean valid = true;

        do {
            try {
                num = console.nextLong();
                valid = false;
            } catch (Exception e) {
                console.next();
                console.nextLine();
                System.out.println("You did not enter a number");
            }
        } while (valid);

        return num;
    }






}

