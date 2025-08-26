package com.iassure.incident.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 *
 * @author Sravanth T
 *
 */
@Entity
@Table(name = "notification_details")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_notification_id")
    private int notificationId;

    @Column(name = "notification_subject")
    private String notificationSubject;

    @Column(name = "notification_module")
    private String notificationModule;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "notification_url")
    private String notificationUrl;

    @Column(name = "created_on")
    private Timestamp createdTimeStamp;

    @Column(name = "notification_desc")
    private String notificationDesc;

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationSubject() {
        return notificationSubject;
    }

    public void setNotificationSubject(String notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }


    public String getNotificationModule() {
        return notificationModule;
    }

    public void setNotificationModule(String notificationModule) {
        this.notificationModule = notificationModule;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }


    public Timestamp getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }


    public String getNotificationDesc() {
        return notificationDesc;
    }

    public void setNotificationDesc(String notificationDesc) {
        this.notificationDesc = notificationDesc;
    }


    @Override
    public String toString() {
        return "Notification [notificationId=" + notificationId + ", notificationSubject=" + notificationSubject
                + ", notificationModule=" + notificationModule + ", notificationType=" + notificationType
                + ", notificationUrl=" + notificationUrl + ", createdTimeStamp=" + createdTimeStamp
                + ", notificationDesc=" + notificationDesc + "]";
    }
}