package com.spring.order.order.Service;


import com.spring.order.Exception.BusinessException;
import com.spring.order.customer.CustomerClient;
import com.spring.order.kafka.OrderConfirmation;
import com.spring.order.kafka.OrderProducer;
import com.spring.order.order.DTO.Mapper.OrderMapper;
import com.spring.order.order.DTO.OrderRequest;
import com.spring.order.order.DTO.OrderResponse;
import com.spring.order.order.Repository.OrderRepository;
import com.spring.order.orderLine.DTO.OrderLineRequest;
import com.spring.order.orderLine.Service.OrderLineService;
import com.spring.order.payment.PaymentClient;
import com.spring.order.payment.PaymentRequest;
import com.spring.order.product.DTO.PurchaseRequest;
import com.spring.order.product.service.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        log.info("🚀 Starting order creation for reference: {}", request.reference());

        // 1️⃣ جلب العميل
        log.info("🔍 Fetching customer with ID: {}", request.customerId());
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> {
                    log.error("❌ No customer found with ID: {}", request.customerId());
                    return new BusinessException("Cannot create order:: No customer exists with the provided ID");
                });

        // 2️⃣ شراء المنتجات
        log.info("🛒 Purchasing products: {}", request.products());
        var purchasedProducts = productClient.purchaseProducts(request.products());

        // 3️⃣ حفظ الطلب
        log.info("💾 Saving order to DB");
        var order = this.repository.save(mapper.toOrder(request));

        // 4️⃣ حفظ تفاصيل الطلب
        for (PurchaseRequest purchaseRequest : request.products()) {
            log.info("📦 Saving order line for product ID: {}", purchaseRequest.productId());
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // 5️⃣ إرسال طلب الدفع
        log.info("💳 Sending payment request for order ID: {}", order.getId());
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        paymentClient.requestOrderPayment(paymentRequest);

        // 6️⃣ إرسال رسالة التأكيد
        log.info("📧 Sending order confirmation for reference: {}", request.reference());
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        log.info("✅ Order created successfully with ID: {}", order.getId());
        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}