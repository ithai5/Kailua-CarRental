import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ManageCar {

    //returns a table with all the information about a specific car
    //SELECT * car information by WHERE criteria
    public static void searchCar(String whereQuery)
        ResultSet rs = DBInteraction.getData("SELECT CarInfo.licencePlate, CarInfo.registration, CarInfo.odometer, Specs.brand, Specs.model, Specs.ccm, Specs.gear, Specs.airCondition, Specs.cruiseControl, Specs.leatherSeat, Specs.seatNumber, Specs.horsePower, Specs.fuelType, ClassType.className, ClassType.pricePerDay" + "" +
                "FROM KeaProject.CarInfo" +
                "INNER JOIN KeaProject.Specs ON Specs.specs_id = CarInfo.specs_id" +
                "INNER JOIN KeaProject.ClassType ON ClassType.className_id = Specs.className_id" +
                whereQuery);
    //!!! TENGO QUE PONER UN SCANNER QUE LEA LA whereQuery del usuario y poner "WHERE" + en el code de mi ResultSet
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-7s%-7s%-7s%-7s%-7s%-15s%-15s%-15s\n","LicencePlate", "Registration", "Odometer", "Brand", "Model", "Ccm", "Gear", "Air Condition", "Cruise Control", "Leather Seat", "Seat Number", "Horsepower", "Fuel Type", "Class", "Price Per Day");
        System.out.println(_____________________________________________________________________________________________________________________________________________);
        try{
            while(rs.next()){
                System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-7s%-7s%-7s%-7s%-7s%-15s%-15s%-15s\n" + rs.getString("licencePlate"), rs.getString("registration"), rs.getString("odometer"), rs.getString("brand"), rs.getString("model"), rs.getString("ccm"), rs.getString("gear"), rs.getString("airCondition"), rs.getString("cruiseControl"), rs.getString("leatherSeat"), rs.getString("seatNumber"), rs.getString("horsePower"), rs.getString("fuelType"), rs.getString("className"),rs.getString("PricePerDay"));
            }
        catch(SQLException e) {
                e.printStackTrace();
        }
    }

    //prints all cars
    public static void printAllCars()
    ResultSet rs = DBInteraction.getData("SELECT CarInfo.licencePlate, CarInfo.registration, CarInfo.odometer, Specs.brand, Specs.model, Specs.ccm, Specs.gear, Specs.airCondition, Specs.cruiseControl, Specs.leatherSeat, Specs.seatNumber, Specs.horsePower, Specs.fuelType, ClassType.className, ClassType.pricePerDay" + "" +
            "FROM KeaProject.CarInfo" +
            "INNER JOIN KeaProject.Specs ON Specs.specs_id = CarInfo.specs_id" +
            "INNER JOIN KeaProject.ClassType ON ClassType.className_id = Specs.className_id");
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-7s%-7s%-7s%-7s%-7s%-15s%-15s%-15s\n","LicencePlate", "Registration", "Odometer", "Brand", "Model", "Ccm", "Gear", "Air Condition", "Cruise Control", "Leather Seat", "Seat Number", "Horsepower", "Fuel Type", "Class", "Price Per Day");
        System.out.println(_____________________________________________________________________________________________________________________________________________);
        try{
        while(rs.Next()){
            System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-7s%-7s%-7s%-7s%-7s%-15s%-15s%-15s\n" + rs.getString("licencePlate"), rs.getString("registration"), rs.getString("odometer"), rs.getString("brand"), rs.getString("model"), rs.getString("ccm"), rs.getString("gear"), rs.getString("airCondition"), rs.getString("cruiseControl"), rs.getString("leatherSeat"), rs.getString("seatNumber"), rs.getString("horsePower"), rs.getString("fuelType"), rs.getString("className"),rs.getString("PricePerDay"));
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    //createQueryForAddCar
    public static String AddCar(){

    }


    //pensar si necesito un ResultSet rs = DBInteraction.getData("UPDATE .....
    public static String updateCar(String licencePlate){
        Scanner console = new Scanner(System.in);
        System.out.print("Type the licence plate of the car you want to delete: ")
        licencePlate = console.next();
        System.out.println("What would you like to change?\n" +
                "[1] Air condition\n" +
                "[2] Cruise control\n" +
                "[3] Leather seat\n" +
                "[4] Price per day\n");
        int userInput = ScannerReader.scannerInt(1, 4);
        int userChange;
        switch (userInput) {
            case 1:
                System.out.println("Type 1 if the car has air condition or 0 if it has not: ");
                userChange = ScannerReader.scannerInt(0, 1);
                return "UPDATE KeaProject.Specs SET airCondition = '" userChange + "' WHERE licencePlate = " + licencePlate;
            case 2:
                System.out.println("Type 1 if the car has cruise control or 0 if it has not: ");
                userChange = ScannerReader.scannerInt(0, 1);
                return "UPDATE KeaProject.Specs SET cruiseControl = '" userChange + "' WHERE licencePlate = " + licencePlate;
            case 3:
                System.out.println("Type 1 if the car has leather seat or 0 if it has not: ");
                userChange = ScannerReader.scannerInt(0, 1);
                return "UPDATE KeaProject.Specs SET leatherSeat = '" userChange + "' WHERE licencePlate = " + licencePlate;
            case 4:
                System.out.println("Type the new price per day: ");
                userChange = ScannerReader.scannerInt();
                return "UPDATE KeaProject.Specs JOIN KeaProject.ClassType ON Specs.className_id = ClassType.className_id " +
                        "SET pricePerDay = '" userChange + "' WHERE licencePlate = " + licencePlate;
            default:
                return null;
        }
    }


    //delete car by licence plate
    public static String deleteCar(String licencePlate){
        Scanner console = new Scanner(System.in);
        System.out.print("Type the licence plate of the car you want to delete: ")
        licencePlate = console.next();
        ResultSet rs = DBInteraction.getData("DELETE CarInfo, Specs, ClassType FROM CarInfo" +
                "INNER JOIN KeaProject.Specs ON Specs.specs_id = CarInfo.specs_id" +
                "INNER JOIN KeaProject.ClassType ON ClassType.className_id = Specs.className_id" +
                "WHERE CarInfo.licencePlate = " + licencePlate); // do I need to specify here also "KeaProject." ?
    }


}
