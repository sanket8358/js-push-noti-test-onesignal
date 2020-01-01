package com.pushnotification.repository;

import com.pushnotification.model.BroadCastedNotifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BroadcastedPushNotificationRepository extends
        JpaRepository<BroadCastedNotifications, Long> {
}
