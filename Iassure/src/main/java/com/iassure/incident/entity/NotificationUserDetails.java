package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
/**
 *
 * @author Sravanth T
 *
 */
@Table(name = "notification_user_details")
@Entity
@Getter
@Setter
public class NotificationUserDetails {

    @Id
    @Column(name = "pk_user_notification_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pknotificationUserId;

    @Column(name = "fk_notification_Id")
    private int fkNotificationId;

    @Column(name = "is_read")
    private int isRead;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "read_on")
    private Timestamp readOn;

    @Column(name = "fk_user_id")
    private int userId;
}