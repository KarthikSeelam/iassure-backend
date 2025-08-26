package com.iassure.service;

import com.iassure.entity.UserDetails;
import com.iassure.incident.entity.Notification;
import com.iassure.incident.dto.SupplierDTO;
import com.iassure.user.dto.OrganizationDTO;

import java.util.List;

/**
 *
 * @author Sravanth T
 */
public interface OrganizationService {

    public OrganizationDTO retrieveOrgDetailsById(int id);
    public SupplierDTO retrieveSupplierDetailsById(int id);
    public Notification saveNotification(Notification notification);
    public void saveNotificationUserDetails(List<UserDetails> userDetails, int notificationId);
}