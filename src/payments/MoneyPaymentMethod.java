package payments;

import enums.Currency;

public abstract class MoneyPaymentMethod implements PaymentMethod, Comparable<PaymentMethod> {

    public abstract Currency getCurrency();
}
