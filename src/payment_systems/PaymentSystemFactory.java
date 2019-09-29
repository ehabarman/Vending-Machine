package payment_systems;

public interface PaymentSystemFactory {

    public PaymentSystem buildSystem(String systemType) throws Exception;
}
