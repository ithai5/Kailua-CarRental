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

    
}
