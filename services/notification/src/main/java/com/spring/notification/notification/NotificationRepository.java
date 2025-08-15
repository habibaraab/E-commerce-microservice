package com.spring.notification.notification;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
