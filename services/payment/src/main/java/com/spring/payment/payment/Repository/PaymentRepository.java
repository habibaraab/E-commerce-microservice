package com.spring.payment.payment.Repository;

import com.spring.payment.payment.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
