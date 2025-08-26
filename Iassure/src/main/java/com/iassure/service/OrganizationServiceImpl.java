package com.iassure.service;

import com.iassure.entity.OrganizationDetails;
import com.iassure.entity.UserDetails;
import com.iassure.incident.entity.Notification;
import com.iassure.incident.entity.NotificationUserDetails;
import com.iassure.incident.entity.Supplier;
import com.iassure.incident.dto.SupplierDTO;
import com.iassure.incident.repository.NotificationRepository;
import com.iassure.incident.repository.NotificationUserDetailsRepository;
import com.iassure.incident.repository.SupplierRepository;
import com.iassure.repositary.OrganizationDetailsRepository;
import com.iassure.user.dto.OrganizationDTO;
import com.iassure.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Sravanth T
 */
@Service
public class OrganizationServiceImpl implements  OrganizationService{

    @Autowired
    OrganizationDetailsRepository organizationDetailsRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationUserDetailsRepository notificationUserDetailsRepository;
    @Autowired
    DateUtil dateUtil;

    @Override
    public OrganizationDTO retrieveOrgDetailsById(int id) {
        OrganizationDTO result = new OrganizationDTO();
        OrganizationDetails orgData = organizationDetailsRepository.findByOrganizationId(id);
        BeanUtils.copyProperties(orgData,result);
        return result;
    }
    @Override
    public SupplierDTO retrieveSupplierDetailsById(int id) {
        SupplierDTO result = new SupplierDTO();
        Supplier supplierData = supplierRepository.findByPkSupplierId(id);
        BeanUtils.copyProperties(supplierData,result);
        return result;
    }
    @Override
    public Notification saveNotification(Notification notification) {
        try {
            notificationRepository.save(notification);
        } catch (Exception e) {
            System.out.println("Exception occured in the saveNotification method" + e.getMessage());
        }
        return notification;
    }
    @Override
    public void saveNotificationUserDetails(List<UserDetails> userDetails, int notificationId) {
        try {
            if(userDetails != null && !userDetails.isEmpty()) {
                userDetails.forEach(userDetail -> {
                    NotificationUserDetails notificationUserDetail = new NotificationUserDetails();
                    notificationUserDetail.setFkNotificationId(notificationId);
                    //Set Is Read Flag is 0 denotes its read
                    notificationUserDetail.setIsRead(0);
                    notificationUserDetail.setUserId(userDetail.getUserId());
                    notificationUserDetail.setCreatedOn(dateUtil.getTimestamp());
                    notificationUserDetailsRepository.save(notificationUserDetail);
                });
            }
        } catch (Exception e) {
            System.out.println("Exception occured in the saveNotificationUserDetails method" + e.getMessage());
        }
    }
}