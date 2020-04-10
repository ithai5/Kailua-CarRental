import java.sql.SQLException;

public class Menu {

    public static void mainMenu() throws SQLException {
        System.out.println("Welcome to Kailua Car Rental");
        boolean keepLooping = true;

        while (keepLooping) {
            System.out.println("Please choose an option\n[1]. Manage Customers\n[2]. Manage Cars\n[3]. Manage Contracts\n[0]. Exit Program");
            System.out.print("Your input: ");
            int choice = ScannerReader.scannerInt(0,3);
            System.out.println();

            switch (choice) {
                case 1:
                    customerMenu();
                    break;
                case 2:
                    carMenu();
                    break;
                case 3:
                    contractMenu();
                    break;
                case 0:
                    System.out.println("Bye bye for now!");
                    return;
            }
            System.out.println();
        }
    }

    public static void createFullCustomer() throws SQLException {
        //Create new Customer
        DBInteraction.updateDatabase(ManageCustomer.newCustomerQuery());
        //Create new Phone
        int newCustomerId = QueryUtility.chooseRowFromList(QueryUtility.getList("Customer", ""),
                QueryUtility.tableSize("Customer", ""));
        DBInteraction.updateDatabase(ManagePhone.newPhoneQuery(newCustomerId));
        //Create new DriverInfo
        DriverInfo.createQueryForAddingDriver();
        System.out.println("New customer has been created!");
        System.out.println();
    }

    public static void customerMenu() throws SQLException {
        boolean keepLooping = true;
        while (keepLooping) {
            System.out.println("Please choose an option\n[1]. Create new customer\n[2]. List details from all customers\n[3]. Search customer details\n[4]. Update customer info\n[5]. Delete customer\n[0]. Return to main menu\n");
            System.out.print("Your input: ");
            int choice = ScannerReader.scannerInt(0, 5);
            System.out.println();

            switch (choice) {
                case 1:
                    createFullCustomer();
                    break;
                case 2:
                    viewCusMenu();
                    break;
                case 3:
                    searchCusMenu();
                    break;
                case 4:
                    updateCusMenu();
                    break;
                case 5:
                    deleteCusMenu();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    keepLooping = false;
                    break;
            }
            System.out.println();
        }
    }

    public static void viewCusMenu() throws SQLException {
        System.out.println("Which list would you like to view?\n[1]. Customers\n[2]. Phones\n[3]. Drivers\n[0]. Cancel");
        System.out.print("Your input: ");
        int choice = ScannerReader.scannerInt(0, 3);
        System.out.println();

        switch (choice) {
            case 1:
                ManageCustomer.printCustomerList("");
                break;
            case 2:

                ManagePhone.printPhoneList("");
                break;
            case 3:
                DriverInfo.seeDrivers();
                break;
            case 0:
                System.out.println("Cancelling...");
                break;
        }
        System.out.println();
    }

    public static void searchCusMenu() throws SQLException {
        System.out.println("Which list would you like to search in?\n[1]. Customers\n[2]. Phones\n[3]. Drivers\n[0]. Cancel");
        System.out.print("Your input: ");
        int choice = ScannerReader.scannerInt(0, 3);
        System.out.println();

        switch (choice) {
            case 1:
                int customerId = ManageCustomer.findCustomerId();
                ManageCustomer.printCustomerList("Customer.customer_id = " + customerId);
                break;
            case 2:
                int phoneId = ManagePhone.findPhoneId();
                ManagePhone.printPhoneList("Phone.phone_id = " + phoneId);
                break;
            case 3:
                DriverInfo.searchSpecificDriver();
                break;
            case 0:
                System.out.println("Cancelling...");
                break;
        }
        System.out.println();
    }

    public static void updateCusMenu() throws SQLException {
        System.out.println("From which list would you like to update?\n[1]. Customers\n[2]. Phones\n[3]. Drivers\n[0]. Cancel");
        System.out.print("Your input: ");
        int choice = ScannerReader.scannerInt(0, 3);
        System.out.println();

        switch (choice) {
            case 1:
                DBInteraction.updateDatabase(ManageCustomer.updateCustomerQuery());
                break;
            case 2:
                DBInteraction.updateDatabase(ManagePhone.updatePhoneQuery());
                break;
            case 3:
                DriverInfo.updateDriver();
                break;
            case 0:
                System.out.println("Cancelling...");
                break;
        }
        System.out.println();
    }

    public static void deleteCusMenu() throws SQLException {
        System.out.println("From which list would you like to delete?\n[1]. Customers\n[2]. Phones\n[3]. Drivers\n[0]. Cancel");
        System.out.print("Your input: ");
        int choice = ScannerReader.scannerInt(0, 3);
        System.out.println();

        switch (choice) {
            case 1:
                ManageCustomer.deleteCustomerQuery();
                break;
            case 2:
                ManagePhone.deletePhoneQuery();
                break;
            case 3:
                DriverInfo.deleteDriver();
                break;
            case 0:
                System.out.println("Cancelling...");
                break;
        }
        System.out.println();
    }

    public static void carMenu(){
        boolean keepLooping = true;
        while (keepLooping) {
            System.out.println("Please choose an option");
            System.out.println("[1]. View all cars");
            System.out.println("[2]. Search car");
            System.out.println("[3]. Add new car");
            System.out.println("[4]. Update car info");
            System.out.println("[5]. Delete car");
            System.out.println("[0]. Quit");
            System.out.print("Your input: ");
            int userInput = ScannerReader.scannerInt(0, 5);
            System.out.println();

            switch (userInput) {
                case 1:
                    ManageCar.printAllCars();
                    break;
                case 2:
                    ManageCar.searchCar();
                    break;
                case 3:
                    ManageCar.addCarInfo();
                    break;
                case 4:
                    ManageCar.updateCar();
                    break;
                case 5:
                    ManageCar.printAllCars();
                    ManageCar.deleteCar();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    keepLooping = false;
                    break;
                default:
                    System.out.println("Something went wrong");
                    break;
            }
            System.out.println();
        }
    }

    public static void contractMenu () throws SQLException {
        boolean keepLooping = true;
        while (keepLooping) {
            System.out.println("Please choose an option");
            System.out.println("[1]. Create new contract\n" +
                    "[2]. Delete contract\n" +
                    "[3]. Update contract\n" +
                    "[4]. View contract\n" +
                    "[5]. Search contract\n" +
                    "[0]. Return to main-menu");
            System.out.print("Your input: ");
            int userInput = ScannerReader.scannerInt(0, 5);
            System.out.println();

            switch (userInput) {
                case 0:
                    System.out.println("Returning to main menu...");
                    keepLooping = false;
                    break;
                case 1:
                    ManageRentalContract.rentCarMenu();
                    break;
                case 2:
                    ManageRentalContract.deleteRentalContract();
                    break;
                case 3:
                    ManageRentalContract.updateContract();
                    break;
                case 4:
                    ManageRentalContract.viewRentalContract("");
                    break;
                case 5:
                    ManageRentalContract.viewRentalContract(ManageRentalContract.searchInTable());
                    break;
            }
            System.out.println();
        }
    }
}
