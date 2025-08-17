package com.spring.payment.payment.Service;


import com.spring.payment.notification.NotificationProducer;
import com.spring.payment.notification.PaymentNotificationRequest;
import com.spring.payment.payment.DTO.PaymentMapper.PaymentMapper;
import com.spring.payment.payment.DTO.PaymentRequest;
import com.spring.payment.payment.Model.Payment;
import com.spring.payment.payment.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        Payment payment = this.repository.save(this.mapper.toPayment(request));

        this.notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}