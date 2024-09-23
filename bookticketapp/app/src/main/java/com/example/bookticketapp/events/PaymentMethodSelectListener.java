package com.example.bookticketapp.events;

import com.example.bookticketapp.models.PaymentMethod;

public interface PaymentMethodSelectListener {
    public void updatePaymentMethod(PaymentMethod payment);
    public void deletePaymentMethod(PaymentMethod payment);
}
