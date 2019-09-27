import machines.VendingMachineFactoryBuilder;
import machines.food.FoodVendingMachineFactory;
import machines.food.snack.SnackVendingMachine;

public class Driver {

    public static void main(String[] args) {

        System.out.println( (int)('E'-'A') );


        System.out.println("===============================================================================");
        System.out.println("Flow 1:-");
        System.out.println("");
        flow1();
        System.out.println("");
        System.out.println("===============================================================================");


    }

    public static void flow1(){
        FoodVendingMachineFactory foodVendingMachineFactory = (FoodVendingMachineFactory) VendingMachineFactoryBuilder.
                buildFactory(VendingMachineFactoryBuilder.FOOD_VENDING_MACHINE_FACTORY);
        SnackVendingMachine snackVendingMachine = (SnackVendingMachine) foodVendingMachineFactory.buildMachine(
                foodVendingMachineFactory.SNACK_VENDING_MACHINE);

        printSnackMachinePrices(snackVendingMachine);
        initSnackMachine(snackVendingMachine);
        printSnackMachinePrices(snackVendingMachine);

    }
    public static void initSnackMachine( SnackVendingMachine snackVendingMachine) {
        int rows = snackVendingMachine.ROWS;
        int cols = snackVendingMachine.COLS;
        int maxSize = snackVendingMachine.MAX_SIZE;
        String [][] items = {
                {"Galaxy","Dairy Milk","Small MR Chips","Small Tiger","Cake"},
                {"Bubbles","moon","small lotus","Azka"," Tirma"},
                {"Flutes","sharks","Dairy Milk Bubbles","Galaxy Bubbles","big lotus"},
                {"Medium MR Chips","Medium Tiger","Medium Flutes","Jum","Taco"},
                {"Large MR Chips","Large Tiger","Large Flutes","Large Galaxy","Large Cake"},
        };

        // initialize prices to the row number
        // row 1 = 1, row 2 = 2, ..... row 5 = 5
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                snackVendingMachine.setPrice(i,j,i+1);

        // fill inventory with items
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                snackVendingMachine.fillInventorySlot(i,j,items[i][j],maxSize);
            }
    }

    public static void printSnackMachinePrices(SnackVendingMachine snackVendingMachine){
        int rows = snackVendingMachine.ROWS;
        int cols = snackVendingMachine.COLS;

        for ( int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++)
                System.out.print(snackVendingMachine.getPrice(i,j)+" ");
            System.out.println();
        }

    }
}
