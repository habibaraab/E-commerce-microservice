package com.spring.notification.notification;


import com.spring.notification.kafka.order.OrderConfirmation;
import com.spring.notification.kafka.payment.PaymentConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Notification {
    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime notificationDate;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;
}
