package com.pushnotification.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Weslei Dias.
 */
@Entity(name="userNotifications")
public class OneSignalPushNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="notificationId")
    private Long notificationId;

    @Column(name="userRegistrationId")
    private String userRegistrationId;

    @Column(name="title")
    private String title;

    @Column(name="message")
    private String message;

    @Column(name="dateTime")
    private Date dateTime;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserRegistrationId() {
        return userRegistrationId;
    }

    public void setUserRegistrationId(String userRegistrationId) {
        this.userRegistrationId = userRegistrationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
