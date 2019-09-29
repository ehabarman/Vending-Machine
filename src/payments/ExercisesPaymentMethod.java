package payments;

public abstract class ExercisesPaymentMethod implements PaymentMethod{

    public abstract int getTimes();
    public abstract String getActions();
}
