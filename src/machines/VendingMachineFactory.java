package machines;

public interface VendingMachineFactory {

    public VendingMachine buildMachine(String machineType) throws Exception;
}
