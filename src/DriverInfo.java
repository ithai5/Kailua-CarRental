//Driver info class from Nesrin
package com.jetbrains;

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
                "[1] View All Drivers \n" +
                "[2] Search Specific Drivers \n" +
                "[3] Edit Existing Driver Information \n" +
                "[4] Create New Driver \n" +
                "[0] Return to the menu");
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


    public static void EditSpecificDriver(){ //turned into void

        //String query = searchSpecificDriver();

        System.out.println("What would you like to do \n" +
                "[1] Update Specific Driver \n" +
                "[2] Delete Specific Driver \n" +
                "[0] Return to the menu");
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
        String driverLicenceNumber = ScannerReader.scannerIntAsString(20);

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
        String licence = ScannerReader.scannerWords();
        String query = null;
        System.out.println("what would you like to change:\n" +
                "[1] Licence Number\n"+
                "[2] First Name\n"+
                "[3] Last Name \n"+
                "[4] Driver Since Date\n"+
                "[5] Customer ID\n"+
                "[0] Quit");

        int userInput = ScannerReader.scannerInt(0, 5);
        String newValue;

        switch (userInput) {
            case 0:
                return;
            case 1:
                System.out.println("Please type the new licence number ");
                newValue = ScannerReader.scannerWords();
                //return "UPDATE  KeaProject.DriverInfo SET driverLicenceNumber = '" + newValue + "' WHERE DriverLicenceNumber = " + licence;
                query = "UPDATE  KeaProject.DriverInfo SET driverLicenceNumber = '" + newValue + "' WHERE DriverLicenceNumber = " + licence;
                DBInteraction.updateDatabase(query);
                break;
            case 2:
                System.out.println("Please type the new first name");
                newValue = ScannerReader.scannerWords();
                //return "UPDATE  KeaProject.DriverInfo SET driverFirstName = '" + newValue + "' WHERE DriverLicenceNumber = " + licence;
                query = "UPDATE  KeaProject.DriverInfo SET driverFirstName = '" + newValue + "' WHERE DriverLicenceNumber = " + licence;
                DBInteraction.updateDatabase(query);
                break;
            case 3:
                System.out.println("Please type the new last name");
                newValue = ScannerReader.scannerWords();
                //return "UPDATE  KeaProject.DriverInfo SET driverLastName = '" + newValue + "' WHERE DriverLicenceNumber = " + licence;
                query = "UPDATE  KeaProject.DriverInfo SET driverLastName = '" + newValue + "' WHERE DriverLicenceNumber = " + licence;
                DBInteraction.updateDatabase(query);
                break;
            case 4:
                System.out.println("Please type the Driver Since date you wish to change to");
                int newDate = ScannerReader.scannerInt();
                //return "UPDATE  KeaProject.DriverInfo SET driverSinceDate = '" + newDate + "' WHERE DriverLicenceNumber = " + licence;
                query = "UPDATE  KeaProject.DriverInfo SET driverSinceDate = '" + newDate + "' WHERE DriverLicenceNumber = " + licence;
                DBInteraction.updateDatabase(query);
                break;
            case 5:
                System.out.println("Please type the customer id you wish to change to");
                int newId = ScannerReader.scannerInt();
                //return "UPDATE  KeaProject.DriverInfo SET customer_id = '" + newId + "' WHERE DriverLicenceNumber = " + licence;
                query = "UPDATE  KeaProject.DriverInfo SET customer_id = '" + newId + "' WHERE DriverLicenceNumber = " + licence;
                DBInteraction.updateDatabase(query);
                break;
            default: //break;

                return;

        }
        //call DBInteraction.updateDatabase(query)

    }

        // collecting the information from the user and return a query for the DB.
        public static void createQueryForAddingDriver () { //changed to void

            //System.out.println("To add a driver, please input the corresponding customer_id");
            //int customer_id = ScannerReader.scannerInt(11);
            //needs to search from existing customer number -- ask Thomas
            System.out.println("Please type driver's licence number");
            String driverLicenceNumber = ScannerReader.scannerWords(); // referring to Itai's Scanner Reader class. Makes sure that the user is input the correct values.
            System.out.println("please type first name");
            String driverFirstName = ScannerReader.scannerWords(); //
            System.out.println("Please type last name");
            String driverLastName = ScannerReader.scannerWords();
            System.out.println("Please type driver since date");
            int driverSinceDate = ScannerReader.scannerInt();

            String query = " VALUES ('" + driverLicenceNumber + "', '" + driverFirstName + "', '" + driverLastName + "', '" + driverSinceDate + "')"; //make sure to add customer id later
            String completeQuery = "INSERT INTO KeaProject.DriverInfo (driverLicenceNumber, driverFirstName, driverLastName, customer_id, driverSinceDate)" + query;
            DBInteraction.updateDatabase(completeQuery);
        }


    //---------------------------------------------------------------
    //SEARCHING METHODS IN MENU
    //---------------------------------------------------------------


        //show all the drivers
        public static void seeDrivers()
        {
            String query = "SELECT * FROM KeaProject.DriverInfo"; //create a query to the DB
            //DBInteraction.getData(query);
            showResultSetOfaDriver(query);

        }

        public static String searchSpecificDriver () {
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
            //ResultSet rs = getQueryResultSet(query); //getting ResultSet from the DB
            System.out.printf("%-25s%-20s%-20s%-20s%-20s\n", "driverLicenceNumber", "driverFirstName", "driverLastName", "DriverSinceDate", "customer_id");
            System.out.println("________________________________________________________________________________________");
            //int i = 0;
            try {
                while (rs.next()) { //iterating throw the resultSet and print it out
                    System.out.printf("%-25s%-20s%-20s%-20s%-20s\n", (rs.getString("driverLicenceNumber")),
                            (rs.getString("driverFirstName") + " " +
                                    rs.getString("driverLastName")), rs.getString("DriverSinceDate"), rs.getString("customer_id")); //make sure to use Thomas's class
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    //method to find which search field the user would like to search in the Driver table
        public static String searchField () {

            //user will choose the field to search in
            System.out.println("What would you like to search by?\n" +
                    "[1] First Name\n" +
                    "[2] Last Name\n" +
                    "[3] Drivers Licence\n" +
                    "[4] Driver Since Date\n" +
                    "[5] Customer number\n");
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
            String selectQuery = "SELECT driversLicenceNumber, driverFirstName, driverLastName, driverSinceDate, customer_id FROM KeaProject.DriverInfo";
            System.out.println("Search: ");
            String userInput = ScannerReader.scannerAll();
            String whereQuery = " WHERE " + searchField + " = '" + userInput + "'";
            String query = selectQuery + whereQuery;
            showResultSetOfaDriver(query);
            DBInteraction.getData(query); //double-check that this is needed here
            return query;
        }



}

