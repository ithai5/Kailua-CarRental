import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageCar {

    //returns a table with all the information about a specific car
    //SELECT * car information by WHERE criteria
    public static void searchCar(){
        Scanner console = new Scanner(System.in);
        System.out.print("Type the attribute of the car you want to search for (no spaces and if there is a second word it must start with capital letter. For example: 'brand', 'model', 'gear', 'seatNumber', 'fuelType'...\n");
        String attribute = console.next();
        System.out.print("Type the parameter of the car you want to search for. For example: 'Audi', 'A8', 'automatic', '5', 'petrol'...\n");
        String parameter = console.next();
        ResultSet rs = DBInteraction.getData("SELECT CarInfo.licencePlate, CarInfo.specs_id, CarInfo.registration, CarInfo.odometer, Specs.brand, Specs.model, Specs.ccm, Specs.gear, Specs.airCondition, Specs.cruiseControl, Specs.leatherSeat, Specs.seatNumber, Specs.horsePower, Specs.fuelType, Specs.className_id, ClassType.className, ClassType.pricePerDay " + "" +
                "FROM KeaProject.CarInfo " +
                "INNER JOIN KeaProject.Specs ON Specs.specs_id = CarInfo.specs_id " +
                "INNER JOIN KeaProject.ClassType ON ClassType.className_id = Specs.className_id " +
                "WHERE " + attribute + " = " + "'" + parameter + "'" + ";");
        System.out.printf("%-20s%-15s%-20s%-20s%-15s%-15s%-15s%-15s%-25s%-25s%-25s%-25s%-15s%-15s%-25s%-20s%-15s\n","Licence Plate", "Specs ID", "Registration", "Odometer", "Brand", "Model", "Ccm", "Gear", "Air Condition", "Cruise Control", "Leather Seat", "Seat Number", "Horsepower", "Fuel Type", "Class Name ID", "Class Name", "Price Per Day");
        System.out.println("_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");        System.out.println();
        try {
            while (rs.next()) {
                System.out.printf("%-20s%-15s%-20s%-20s%-15s%-15s%-15s%-15s%-25s%-25s%-25s%-25s%-15s%-15s%-25s%-20s%-15s\n", rs.getString("licencePlate"), rs.getString("specs_id"), rs.getString("registration"), rs.getString("odometer"), rs.getString("brand"), rs.getString("model"), rs.getString("ccm"), rs.getString("gear"), rs.getString("airCondition"), rs.getString("cruiseControl"), rs.getString("leatherSeat"), rs.getString("seatNumber"), rs.getString("horsePower"), rs.getString("fuelType"), rs.getString("className_id"), rs.getString("className"), rs.getString("PricePerDay"));
            }
        } catch(SQLException e) {
                e.printStackTrace();
        }
    }
    public static ResultSet viewSpecs(String whereQuery){
        String query = "SELECT * FROM Specs " + whereQuery;
        ResultSet rs = DBInteraction.getData(query);
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Number", "Brand", "Model", "Ccm", "Gear", "Air Condition", "Cruise Control", "Leather Seat", "Seat Number", "Horse Power", "Fuel Type", "ClassName ID");
        int i = 0;
        try {
            while(rs.next()) {
                i++;
                System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "[" + i + "].", rs.getString("brand"), rs.getString("model"), rs.getString("ccm"), rs.getString("gear"), rs.getString("airCondition"), rs.getString("cruiseControl"), rs.getString("leatherSeat"), rs.getString("seatNumber"), rs.getString("horsePower"), rs.getString("fuelType"), rs.getString("className_id"));
            } rs.beforeFirst();
            return rs;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int chooseSpecId() throws SQLException {
        ResultSet rs = viewSpecs(chooseBrandAndModel());
        if (!rs.next()) {
            return -1;
        }
        System.out.println("Choose one of the options:\n");
        int userInput = ScannerReader.scannerInt();
        rs.absolute(userInput);
        return rs.getInt("specs_id");
    }

    public static String chooseBrandAndModel(){
        System.out.println("Type the brand:\n");
        String brand = ScannerReader.scannerAll();
        System.out.println("Type the model:\n");
        String model = ScannerReader.scannerAll();
        return "WHERE brand LIKE '" + brand + "' AND model LIKE '" + model + "'";
    }

    //prints all cars
    public static void printAllCars(){
    ResultSet rs = DBInteraction.getData("SELECT CarInfo.licencePlate, CarInfo.specs_id, CarInfo.registration, CarInfo.odometer, Specs.brand, Specs.model, Specs.ccm, Specs.gear, Specs.airCondition, Specs.cruiseControl, Specs.leatherSeat, Specs.seatNumber, Specs.horsePower, Specs.fuelType, Specs.className_id, ClassType.className, ClassType.pricePerDay " +
            "FROM KeaProject.CarInfo " +
            "INNER JOIN KeaProject.Specs ON Specs.specs_id = CarInfo.specs_id " +
            "INNER JOIN KeaProject.ClassType ON ClassType.className_id = Specs.className_id;");
        System.out.printf("%-20s%-15s%-20s%-20s%-15s%-15s%-15s%-15s%-25s%-25s%-25s%-25s%-15s%-15s%-25s%-20s%-15s\n","Licence Plate", "Specs_id", "Registration", "Odometer", "Brand", "Model", "Ccm", "Gear", "Air Condition", "Cruise Control", "Leather Seat", "Seat Number", "Horsepower", "Fuel Type", "Class Name ID", "Class Name", "Price Per Day");
        System.out.println("_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println();
        try{
            while(rs.next()) {
                System.out.printf("%-20s%-15d%-20s%-20d%-15s%-15s%-15d%-15s%-25d%-25d%-25d%-25d%-15d%-15s%-25d%-20s%-15s\n", rs.getString("licencePlate"), rs.getInt("specs_id"), rs.getString("registration"), rs.getInt("odometer"), rs.getString("brand"), rs.getString("model"), rs.getInt("ccm"), rs.getString("gear"), rs.getInt("airCondition"), rs.getInt("cruiseControl"), rs.getInt("leatherSeat"), rs.getInt("seatNumber"), rs.getInt("horsePower"), rs.getString("fuelType"), rs.getInt("className_id"), rs.getString("className"), rs.getString("pricePerDay"));
                //System.out.println(rs.getString("licencePlate") + rs.getInt("specs_id") + rs.getString("registration") + rs.getInt("odometer") + rs.getString("brand") + rs.getString("model") + rs.getInt("ccm") + rs.getString("gear")+ rs.getInt("airCondition")+ rs.getInt("cruiseControl")+ rs.getInt("leatherSeat")+ rs.getInt("seatNumber")+ rs.getInt("horsePower")+ rs.getString("fuelType")+ rs.getInt("className_id")+ rs.getString("className")+ rs.getString("pricePerDay"));

            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    //choose specific table
    public static String chooseTable ()
    {
        //user choose the table to operate on
        System.out.println("Which type of information would you like to add:\n[1] CarInfo (Licence plate, Registration, Odometer) \n[2] Specs \n[3] ClassType (Class name, Price per day)");
        int userChoice = ScannerReader.scannerInt(1, 3);
        switch (userChoice) {
            case 1:
                return "CarInfo";
            case 2:
                return "Specs";
            case 3:
                return "ClassType";
            default:
                System.out.println("Something went wrong");
                return null;
        }
    }

    public static void addCarInfo() throws SQLException {
        System.out.println("Type the licence plate number:");
        String licencePlate = ScannerReader.scannerAll();
        System.out.println("Check if there is a similar model in our system:");
        int specId = -1;
        if (ScannerReader.scannerBoolean(3)){
            specId = chooseSpecId();
        } else {
            specId = addSpecs();
        }
        if (specId == -1) {
            System.out.println("The model we are searching for have not been found...");
            return;
        }
        System.out.println("Type the registration date");
        String registration = collectRegistrationDate();
        System.out.println("Type the number of kilometers showing on the odometer: ");
        int odometer = ScannerReader.scannerInt();
        String query = "INSERT INTO KeaProject.CarInfo (licencePlate, registration, odometer, specs_id) VALUES ('" + licencePlate + "', '" + registration + "', " + odometer + ", " + specId + ");";
        DBInteraction.updateDatabase(query);
    }

    public static int addSpecs() {
        System.out.println("Type the brand name:");
        String brand = ScannerReader.scannerWords();
        System.out.println("Type the model name:");
        String model = ScannerReader.scannerAll();
        System.out.println("Type the ccm:");
        int ccm = ScannerReader.scannerInt();
        System.out.println("Type the gear type:");
        String gear = ScannerReader.scannerWords();
        System.out.println("Type 1 if it has air condition or type 0 if it has not");
        int airCondition = ScannerReader.scannerInt();
        System.out.println("Type 1 if it has cruise control or type 0 if it has not");
        int cruiseControl = ScannerReader.scannerInt();
        System.out.println("Type 1 if it has leather seats or type 0 if it has not");
        int leatherSeat = ScannerReader.scannerInt();
        System.out.println("Type the number of seats:");
        int seatNumber = ScannerReader.scannerInt();
        System.out.println("Type the amount of horsepower:");
        int horsePower = ScannerReader.scannerInt();
        System.out.println("Type the fuel type:");
        String fuelType = ScannerReader.scannerAll();
        System.out.println("Type the class name id:\n [1] for Family\n [2] for Sport\n [3] for Luxury\n");
        int classNameId = ScannerReader.scannerInt(1, 3);
        String query = "INSERT INTO KeaProject.Specs (brand, model, ccm, gear, airCondition, cruiseControl, leatherSeat, seatNumber, horsePower, fuelType, className_id) " +
                "VALUES ('" + brand + "', '" + model + "', " + ccm + ", '" + gear + "', " + airCondition + ", " + cruiseControl + ", " + leatherSeat + ", " + seatNumber + ", " + horsePower + ", '" + fuelType + "', " + classNameId + ");";
        DBInteraction.updateDatabase(query);
        ResultSet rs = DBInteraction.getData("SELECT * FROM Specs");
        try {
            rs.last();
            return rs.getInt("specs_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void addClassType(){
        //System.out.println("Type the class name:\n");
        //String className = ScannerReader.scannerWords();
        System.out.println("Type the price per day:");
        int pricePerDay = ScannerReader.scannerInt();
        //in the query I delete className
        String query = "INSERT INTO KeaProject.ClassType (pricePerDay) VALUES (" + pricePerDay + ");";
        DBInteraction.updateDatabase(query);
    }


    public static String collectRegistrationDate() {
        System.out.println("Type the day: ");
        String day = ScannerReader.scannerInt(1, 31) + "";
        //fitting the date format
        if (day.length() < 2) {
            day = "0" + day;
        }
        System.out.println("Type the month: ");
        String month = ScannerReader.scannerInt(1, 12) + "";
        //fitting the date format
        if (month.length() < 2) {
            month = "0" + month;
        }
        System.out.println("Type the year: ");
        int year = ScannerReader.scannerInt(1900, 9999);
        return year + "-" + month + "-" + day;
    }

    public static void updateCar(){
        printAllCars();
        Scanner console = new Scanner(System.in);
        System.out.print("Type the specs_id of the car you want to update:\n");
        int specs_id = console.nextInt();
        System.out.println("What would you like to change?\n" +
                "[1] Air condition\n" +
                "[2] Cruise control\n" +
                "[3] Leather seat\n" +
                "[4] Price per day\n");
        int userInput = ScannerReader.scannerInt(1, 4);
        int userChange;
        String query = "";
        switch (userInput) {
            case 1:
                System.out.println("Type 1 if the car has air condition or 0 if it has not: ");
                userChange = ScannerReader.scannerInt(0, 1);
                query = "UPDATE KeaProject.Specs SET airCondition = '" + userChange + "' WHERE specs_id = " + specs_id + ";";
                DBInteraction.updateDatabase(query);
                break;
            case 2:
                System.out.println("Type 1 if the car has cruise control or 0 if it has not: ");
                userChange = ScannerReader.scannerInt(0, 1);
                query = "UPDATE KeaProject.Specs SET cruiseControl = '" + userChange + "' WHERE specs_id = " + specs_id + ";";
                DBInteraction.updateDatabase(query);
                break;
            case 3:
                System.out.println("Type 1 if the car has leather seat or 0 if it has not: ");
                userChange = ScannerReader.scannerInt(0, 1);
                query = "UPDATE KeaProject.Specs SET leatherSeat = '" + userChange + "' WHERE specs_id = " + specs_id + ";";
                DBInteraction.updateDatabase(query);
                break;
            case 4:
                System.out.println("Type the class name id you want to change.\n [1] for Family\n [2] for Sport\n [3] for Luxury");
                int className_id = ScannerReader.scannerInt(1, 3);
                System.out.println("Type the new price per day: ");
                userChange = ScannerReader.scannerInt();
                query = "UPDATE KeaProject.ClassType JOIN KeaProject.Specs ON ClassType.className_id = Specs.className_id =  " +
                        "SET pricePerDay = " + userChange + " WHERE className_id = " + className_id + ";";
                DBInteraction.updateDatabase(query);
                break;
        }
    }

    //delete car by licence plate
    public static void deleteCar(){
        Scanner console = new Scanner(System.in);
        System.out.print("Type the licence plate of the car you want to delete:\n");
        String licencePlate = console.next();
        String query = "DELETE CarInfo FROM KeaProject.CarInfo " +
                "WHERE CarInfo.licencePlate = '" + licencePlate + "';";
        DBInteraction.updateDatabase(query);
    }

    public static void manageCarMenu() throws SQLException {
            System.out.println("Please, select one of the following options:");
            System.out.println("[1] View all cars");
            System.out.println("[2] Search car");
            System.out.println("[3] Add new car");
            System.out.println("[4] Update car info");
            System.out.println("[5] Delete car");
            System.out.println("[0] Quit");
            int userInput = ScannerReader.scannerInt(0, 5);
            switch (userInput) {
                case 1:
                    printAllCars();
                    break;
                case 2:
                    searchCar();
                    break;
                case 3:
                    addCarInfo();
                    //addSpecs();
                    //addClassType();
                    break;
                case 4:
                    updateCar();
                    break;
                case 5:
                    deleteCar();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Something went wrong");
                    break;
            }
    }

}
