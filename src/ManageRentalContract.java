import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageRentalContract {
    //for minimize the option, can be added where to the query
    public static void viewRentalContract(String whereQuery)
    {
        ResultSet rs = DBinteraction.getData("SELECT Customer.firstName, Customer.lastName, RentalContract.startDate, RentalContract.endDate, CarInfo.licencePlate, Specs.brand, Specs.model,ClassType.className " + "" +
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
        String query = "SELECT licencePlate, startDate, endDate,brand, model, className " +
                "FROM KeaProject.CarInfo " +
                "LEFT JOIN KeaProject.RentalContract USING (licencePlate) " +
                "JOIN KeaProject .Specs USING (specs_id) " +
                "JOIN KeaProject.ClassType USING (className_id) " +
                "WHERE NOT startDate >= '" + startDate +"' AND endDate <= '" + endDate + "' AND className LIKE '%"+ className +"' " +
                "UNION " +
                "SELECT licencePlate, startDate, endDate,brand, model, className " +
                "FROM KeaProject.CarInfo " +
                "LEFT JOIN KeaProject.RentalContract USING (licencePlate) " +
                "JOIN KeaProject .Specs USING (specs_id) " +
                "JOIN KeaProject.ClassType USING (className_id) " +
                "WHERE startDate IS NULL AND className LIKE '%"+ className +"' "+
                "GROUP BY licencePlate";
        ResultSet rs = DBinteraction.getData(query);
        System.out.printf("%-8s%-17s%-15s%-15s%-15s\n","Num","Licence", "Brand", "Model","Class");
        System.out.println("____________________________________________________________________");
        int i = 1;
        try{
            while (rs.next()){
                System.out.printf("%-8s%-17s%-15s%-15s%-15s\n","["+i+"]",rs.getString("licencePlate"),rs.getString("brand"),rs.getString("Model"),rs.getString("className"));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return rs;
    }


    
}
