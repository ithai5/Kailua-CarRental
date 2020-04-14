/*
This class has been created by Itai Gramse. this class should take care about renting a car in a car-rental company. you can find here
interaction between the java code to the database on mysql.
this code is part of a project to kea copenhagen academy.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageRentalContract {
    //for minimize the option, can be added where to the query
    public static String searchInTable ()
    {
        System.out.println("what would you like to search by?" + "\n[1]. First Name" + "\n[2]. Last Name" + "\n[3]. Start Date" + "\n[4]. End Date" + "\n[5]. Car Licence Plate" + "\n[0]. return to the menu");
        String searchTerm = "";
        int userType = ScannerReader.scannerInt(0, 5);
        switch (userType) {
            case 0:
                return null;
            case 1:
                System.out.println("Please type your search");
                searchTerm = "Where firstName LIKE '" + ScannerReader.scannerAll() + "'";
                break;
            case 2:
                System.out.println("Please type your search");
                searchTerm = "Where lastName LIKE '" + ScannerReader.scannerAll() + "'";
                break;
            case 3:
                System.out.println("Please type your search");
                searchTerm = "Where startDate ='" + collectDateInfo("search of") + "'";
                break;
            case 4:
                System.out.println("Please type your search");
                searchTerm = "Where endDate ='" + collectDateInfo("search of") + "'";
                break;
            case 5:
                System.out.println("Please type your search");
                searchTerm = "Where licence plate LIKE '" + ScannerReader.scannerAll() + "'";
                break;
            default:
                break;
        }
        return searchTerm;
    }

    //show contract table, can add where query for minimize the results
    public static ResultSet viewRentalContract (String whereQuery)
    {
        ResultSet rs = DBInteraction.getData("SELECT Customer.firstName, Customer.lastName, RentalContract.startDate, RentalContract.endDate, CarInfo.licencePlate, Specs.brand, Specs.model,ClassType.className, RentalContract.rentalContract_id " + "" + "FROM KeaProject.RentalContract " + "INNER JOIN KeaProject.Customer ON Customer.customer_id = RentalContract.customer_id " + "INNER JOIN KeaProject.CarInfo ON  RentalContract.licencePlate = CarInfo.licencePlate " + "INNER JOIN KeaProject.Specs ON Specs.specs_id = CarInfo.specs_id " + "INNER JOIN KeaProject.ClassType ON Specs.className_id = ClassType.className_id " + whereQuery);
        System.out.printf("%-5s%-20s%-25s%-25s%-15s%-15s%-10s%-10s\n", "Num", "Name", "Start Rental", "End Rental", "Licence Num", "Brand", "Model", "Class");
        System.out.println("___________________________________________________________________________________________________________");

        try {
            int i = 0;
            while (rs.next()) {
                i++;
                System.out.printf("%-5s%-20s%-25s%-25s%-15s%-15s%-10s%-10s\n", "[" + i + "].", rs.getString("firstName") + " " + rs.getString("lastName"), rs.getString("startDate"), rs.getString("endDate"), rs.getString("licencePlate"), rs.getString("brand"), rs.getString("model"), rs.getString("className"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //return result set of available cars between two dates
    public static ResultSet availableCars (String startDate, String endDate, String className)
    {
        String query =  "SELECT licencePlate, brand, model, className " +
                "FROM Specs AS b " +
                "JOIN " +
                "(SELECT CarInfo.licencePlate, startDate, endDate, rentalContract_id, CarInfo.specs_id " +
                "FROM KeaProject.CarInfo AS a " + "JOIN KeaProject.RentalContract ON a.licencePlate= RentalContract.licencePlate " +
                "AND ((startDate >= '" + startDate + "' AND startDate <= '" + endDate + "') " + "OR(endDate >= '" + startDate + "' AND endDate <= '" + endDate + "') " +
                "OR (startDate >= '" + startDate + "' AND endDate <= '" + endDate + "')) " +
                "RIGHT JOIN CarInfo ON a.licencePlate = CarInfo.licencePlate " +
                "WHERE rentalContract_id IS NULL) AS c " + "ON b.specs_id = c.specs_id  " +
                "JOIN KeaProject.ClassType AS d " + "ON b.className_id = d.className_id AND className LIKE '%" + className + "'";
        ResultSet rs = DBInteraction.getData(query); //sending the query to the data base and sign it in as a result set
        System.out.printf("%-8s%-17s%-15s%-15s%-15s\n", "Num", "Licence", "Brand", "Model", "Class");
        System.out.println("____________________________________________________________________");
        int i = 0;//adding numbers to the presentation of the result set
        try {
            while (rs.next()) {
                //looping over the result set and print it out as a table
                i++;
                System.out.printf("%-8s%-17s%-15s%-15s%-15s\n", "[" + i + "].", rs.getString("licencePlate"), rs.getString("brand"), rs.getString("Model"), rs.getString("className"));
            }
            rs.beforeFirst();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if (i == 0) {
            return null;
        }
        return rs;
    }

    //find the price per day by enter the licencePlate of a car
    public static int findPricePerDay (String licencePlate)
    {
        //sending a query to the database for finding the price per day of a car throw the licence plate
        ResultSet rs = DBInteraction.getData("SELECT pricePerDay " +
                                                    "FROM CarInfo " +
                                                    "JOIN Specs USING (specs_id) " +
                                                    "JOIN ClassType USING (className_id) " +
                                                    "WHERE licencePlate LIKE '" + licencePlate + "'");
        try {
            while (rs.next()) {
                //getting the price and return it as an int
                return (rs.getInt("pricePerDay"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //method for updating contract
    public static void updateContract ()
    {
        ResultSet rs = viewRentalContract(searchInTable()); //search for a contract that the customer would like to update
        System.out.println("Which rental contract would you like to update? ");
        try {
            if (rs.next() == true) {
                int userInput = ScannerReader.scannerInt();
                String query = "";
                rs.absolute(userInput); //set the cruiser on the right contract to update
                String rentalContractId = rs.getString("rentalContract_id"); //extract the id number of the contract
                System.out.println("What would you like to update ?" + "\n[1]. Change dates" + "\n[2]. Change customer" + "\n[3]. Change price" + "\n[0]. Return to the menu");
                userInput = ScannerReader.scannerInt(0, 3);
                switch (userInput) {
                    case 0:
                        return;
                    case 1:
                        //changing dates of rent
                        String startDate = collectDateInfo("start");
                        String endDate = collectDateInfo("end");
                        rs = availableCars(startDate, endDate, chooseCarType());
                        rs.beforeFirst();
                        if (rs.next() == true) {
                            System.out.println("Please choose the car that you would like to rent");
                            int userType = ScannerReader.scannerInt();
                            rs.absolute(userType);
                            String licenceNumber = rs.getString("licenceNumber");
                            query = "UPDATE KeaProject.RentalContract " + "SET licenceNumber = '" + licenceNumber + "' " + "startDate = '" + startDate + "' " + "endDate = '" + endDate + "' " + "WHERE rentalContract_id = " + rentalContractId;
                        }
                        else {
                            System.out.println("sorry but there is not available car on those days");
                        }
                        break;
                    case 2:
                        //changing customer fo the rent
                        int customer_id;
                        System.out.println("are you a customer?" + "\n[1] Yes" + "\n[2] No");
                        if (ScannerReader.scannerBoolean(3)) {
                            customer_id = ManageCustomer.findCustomerId();
                        }
                        //Creates new Customer + Phone + Driver
                        else {
                            Menu.createFullCustomer();
                            customer_id = QueryUtility.chooseRowFromList(QueryUtility.getList("Customer", ""), QueryUtility.tableSize("Customer", ""));
                        }
                        query = "UPDATE KeaProject.RentalContract " + "customer_id = " + customer_id + " " + "WHERE rentalContract = " + rentalContractId;
                        break;
                    case 3:
                        System.out.println("Please Type the new Price");
                        int price = ScannerReader.scannerInt(1, 999999999);
                        query = "UPDATE KeaProject.RentalContract " + "price = " + price + " " + "WHERE rentalContract = " + rentalContractId;
                        break;
                }
                if (query.length() > 0) {
                    DBInteraction.updateDatabase(query);
                }
            }
            else {
                System.out.println("no contract have been found");
            }
        }
        catch (SQLException e) {
            System.out.println("something went wrong ");
        }
    }

    //find the odometer of a car
    public static String findKmInCar (String licencePlate)
    {
        String query = "SELECT odometer " + "FROM KeaProject.CarInfo " + "WHERE licencePlate LIKE '" + licencePlate + "'";
        ResultSet rs = DBInteraction.getData(query);
        try {
            while (rs.next()) {
                String odometer = rs.getString("odometer");
                return odometer;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //allow the user to choose a car type
    public static String chooseCarType ()
    {
        System.out.println("Which car type would you like to rent ?\n" + "[1]. Family \n" + "[2]. Sport \n" + "[3]. Luxury \n" + "[4]. Show all options\n" + "[0]. cancel ");
        int userInput = ScannerReader.scannerInt(0, 4);
        switch (userInput) {
            case 0:
                return null;
            case 1:
                return "Family";
            case 2:
                return "Sport";
            case 3:
                return "Luxury";
            case 4:
                return "";
            default:
                System.out.println("Something went wrong");
                return "";
        }
    }

    //this class will be called from the menu class for handle car rent
    public static void rentCarMenu () throws SQLException
    {
        String carClass = chooseCarType();
        String startRent = collectDateInfo("start");
        String endRent = collectDateInfo("end");
        String startRental = Calendar.convertDate(startRent);
        String endRental = Calendar.convertDate(endRent);
        int daysBetween = Calendar.daysInBetween(startRental, endRental);
        ResultSet rs = availableCars(startRent, endRent, carClass);
        int userInput;
        if (rs == null) {
            System.out.println("There is no available car");
            return;
        }
        String licencePlate = "";
        System.out.println("Type the number which car would you like to rent ");
        userInput = ScannerReader.scannerInt();
        try {
            rs.absolute(userInput);
            licencePlate = rs.getString("licencePlate");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        int kmPrice = 250;
        System.out.println("would you like to have km limitation?");
        String kmLimit = "NULL";
        boolean bool = ScannerReader.scannerBoolean(3);
        if (bool) {
            System.out.println("how many km would you like to have ?");
            kmPrice = ScannerReader.scannerInt(1, 1000)/ 3 ;
            kmLimit = kmPrice +"";
        }
        int customer_id = -1;
        while (customer_id < 0) {
            System.out.println("are you a customer?");
            if (ScannerReader.scannerBoolean(3)) {
                // call thomas method to search
                try {
                    customer_id = ManageCustomer.findCustomerId();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                Menu.createFullCustomer();
                try {
                    customer_id = QueryUtility.chooseRowFromList(QueryUtility.getList("Customer", ""), QueryUtility.tableSize("Customer", ""));
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        int pricePerDay = findPricePerDay(licencePlate);
        int finalPrice = daysBetween * pricePerDay + kmPrice;//some math for making the total price
        startRent = "'" + startRent + " 00:00:00'";
        endRent = "'" + endRent + " 00:00:00'";
        String startKm = findKmInCar(licencePlate);
        String query = " VALUES (" + startRent + ", " + endRent + ", " + kmLimit + ", " + startKm + ", '" + licencePlate + "', " + customer_id + ", " + finalPrice + ")";
        String updateQuery = "INSERT INTO KeaProject.RentalContract (startDate, endDate, maxKm, startKm, licencePlate, customer_id, totalPrice )" + query;
        DBInteraction.updateDatabase(updateQuery);
    }

    //allow the user to delete on of the contract that exist in the database
    public static void deleteRentalContract ()
    {
        ResultSet rs = viewRentalContract(searchInTable()); //searching fo the contract that the user would like to delete
        System.out.println("Which rental contract would you like to delete? (for cancel press 0)");
        try {
            rs.beforeFirst();
            if (rs.next() == true) { //checking if there are any result for the search
                int userInput = ScannerReader.scannerInt();
                if (userInput == 0){
                    return;
                }
                rs.absolute(userInput);
                DBInteraction.updateDatabase("DELETE FROM KeaProject.RentalContract " + "WHERE rentalContract_id = " + rs.getString("rentalContract_id"));
            }
            else {
                System.out.println("no contract have been found");
                return;
            }
        }
        catch (SQLException e) {
            System.out.println("something went wrong ");
        }
    }

    public static String collectDateInfo (String request)
    {
        System.out.println("Please state the " + request + " day of rental");
        String day = ScannerReader.scannerInt(1, 31) + "";
        //fitting the date format
        if (day.length() < 2) {
            day = "0" + day;
        }
        System.out.println("Please state the " + request + " month of rental");
        String month = ScannerReader.scannerInt(1, 12) + "";
        //fitting the date format
        if (month.length() < 2) {
            month = "0" + month;
        }
        System.out.println("Please state the " + request + " year of rental");
        int year = ScannerReader.scannerInt(1900, 9999);
        return year + "-" + month + "-" + day;

    }

}
