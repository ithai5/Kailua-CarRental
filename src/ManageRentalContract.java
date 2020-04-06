/*
This class has been created by Itai Gramse. this class should take care about renting a car in a car-rental company. you can find here
interaction between the java code to the database on mysql.
this code is part of a project to kea copenhagen academy.
 */
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageRentalContract {
    //for minimize the option, can be added where to the query
    public static void viewRentalContract(String whereQuery)
    {
        ResultSet rs = DBInteraction.getData("SELECT Customer.firstName, Customer.lastName, RentalContract.startDate, RentalContract.endDate, CarInfo.licencePlate, Specs.brand, Specs.model,ClassType.className " + "" +
                "FROM KeaProject.RentalContract " +
                "INNER JOIN KeaProject.Customer ON Customer.customer_id = RentalContract.customer_id " +
                "INNER JOIN KeaProject.CarInfo ON  RentalContract.licencePlate = CarInfo.licencePlate " +
                "INNER JOIN KeaProject.Specs ON Specs.specs_id = CarInfo.specs_id " +
                "INNER JOIN KeaProject.ClassType ON Specs.className_id = ClassType.className_id " +
                whereQuery);
        System.out.printf("%-20s%-25s%-25s%-15s%-15s%-10s%-10s\n","Name","Start Rental","End Rental","Licence Num","Brand","Model","Class");
        System.out.println("___________________________________________________________________________________________________________");
        try {
            while (rs.next()){
                System.out.printf("%-20s%-25s%-25s%-15s%-15s%-10s%-10s\n",rs.getString("firstName")+ " " + rs.getString("lastName"),rs.getString("startDate"),rs.getString("endDate"),rs.getString("licencePlate"),rs.getString("brand"),rs.getString("model"),rs.getString("className"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //return result set of available cars between two dates
    public static ResultSet availableCars(String startDate, String endDate, String className){
        String query = "SELECT licencePlate, brand, model, className " +
                "FROM Specs AS b " +
                "JOIN " +
                    "(SELECT CarInfo.licencePlate, startDate, endDate, rentalContract_id, CarInfo.specs_id " +
                    "FROM KeaProject.CarInfo AS a " +
                    "JOIN KeaProject.RentalContract ON a.licencePlate= RentalContract.licencePlate " +
                        "AND ((startDate >= '" + startDate + "' AND endDate <= '" + endDate + "') " +
                        "OR (startDate<='" + endDate + "' AND endDate>= '" + startDate + "')) " +
                "RIGHT JOIN CarInfo ON a.licencePlate = CarInfo.licencePlate " +
                "WHERE rentalContract_id IS NULL) AS c " +
                "ON b.specs_id = c.specs_id  " +
                "JOIN KeaProject.ClassType AS d " +
                "ON b.className_id = d.className_id AND className LIKE '%" + className + "'";
        ResultSet rs = DBInteraction.getData(query);
        System.out.printf("%-8s%-17s%-15s%-15s%-15s\n","Num","Licence", "Brand", "Model","Class");
        System.out.println("____________________________________________________________________");
        int i = 0;
        try{
            while (rs.next()){
                i++;
                System.out.printf("%-8s%-17s%-15s%-15s%-15s\n","["+i+"]",rs.getString("licencePlate"),rs.getString("brand"),rs.getString("Model"),rs.getString("className"));

            }
            rs.	beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (i == 0){
            return null;
        }
        return rs;
    }
    public int priceType (String className){
        String query = "SELECT pricePerDay " +
                "FROM KeaProject.ClassType " +
                "WHERE className LIKE " + className;
        ResultSet rs = DBInteraction.getData(query);
        int pricePerDay;
        try {
            pricePerDay = rs.getInt("pricePerDay");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return pricePerDay;
    }

    public static String findKmInCar(String licencePlate){
        String query = "SELECT odometer " +
                "FROM KeaProject.CarInfo " +
                "WHERE licencePlate LIKE '" + licencePlate +"'";
        ResultSet rs = DBInteraction.getData(query);
        try {
            while (rs.next()){
            String odometer = rs.getString("odometer");
            return odometer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //this class will be called from the menu class for handle car rent
    public static void rentCarMenu()
    {
        System.out.println("Which car type would you like to rent ?\n" + "[1] Family \n" + "[2] Sport \n" + "[3] Luxury \n" + "[4] Show all options\n" + "[0] cancel ");
        String carClass;
        int userInput = ScannerReader.scannerInt(0, 4);
        switch (userInput) {
            case 0:
                return;
            case 1:
                carClass = "Family";
                break;
            case 2:
                carClass = "Sport";
                break;
            case 3:
                carClass = "Luxury";
                break;
            case 4:
                carClass = "";
                break;
            default:
                System.out.println("Something went wrong");
                return;

        }
        String startRent= collectDateInfo("start");
        String endRent = collectDateInfo("end");
        ResultSet rs = availableCars(startRent,endRent,carClass);
        if (rs == null){
            return;
        }
        String licencePlate ="";
        System.out.println("Type the number which car would you like to rent ");
        userInput = ScannerReader.scannerInt();
        try {
            rs.absolute(userInput);
            licencePlate = rs.getString("licencePlate");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("would you like to have km limitation?");
        String kmLimit = "null";
        boolean bool = ScannerReader.scannerBoolean(3);
        if (bool) {
            System.out.println("how many km would you like to have ?");
            kmLimit = ScannerReader.scannerInt(1, 1000) + "";
        }
        String customer_id = "6";//choose customer_id
        int finalPrice = 0;//some math for making the total price
        startRent = "'"+ startRent +" 00:00:00'";
        endRent = "'"+ endRent +" 00:00:00'";
        String startKm =  findKmInCar(licencePlate);
        String query = " VALUES (" + startRent + ", "+ endRent + ", " + kmLimit + ", " + startKm + ", '" + licencePlate + "', " + customer_id + ", "  + finalPrice + ")";

        System.out.println(startRent);
        String updateQuery = "INSERT INTO KeaProject.RentalContract (startDate, endDate, maxKm, startKm, licencePlate, customer_id, totalPrice )" + query;
        //" VALUES (" + startRent + "', '"+ endRent + "', " + kmLimit + ", " + startKm + ", '" + licencePlate + "', " + customer_id + ","  + finalPrice + ")";
        DBInteraction.updateDatabase(updateQuery);
    }

        public static String collectDateInfo(String request)
        {
            System.out.println("Please state the " + request +  " day of rental");
            String day = ScannerReader.scannerInt(1, 31) + "";
            //fitting the date format
            if (day.length() < 2) {
                day = "0" + day;
            }
            System.out.println("Please state the " + request +  " month of rental");
            String month = ScannerReader.scannerInt(1, 12) + "";
            //fitting the date format
            if (month.length() < 2) {
                month = "0" + month;
            }
            System.out.println("Please state the " + request +  " year of rental");
            int year = ScannerReader.scannerInt(1900, 9999);
            return year + "-" + month + "-" + day;

        }


}
