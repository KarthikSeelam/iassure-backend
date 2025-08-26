package com.iassure.repositary;


import com.iassure.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    UserDetails findByUsername(String email);

    UserDetails findByContactNo(String mobile);

   @Query("SELECT ud FROM UserDetails ud WHERE ud.contactNo = :mobileNoOrEmail OR ud.email = :mobileNoOrEmail")
   UserDetails findByMobileNoOrEmail(String mobileNoOrEmail);

    List<UserDetails> findByuserIdIn(List<Integer> userIds);
    @Query("SELECT COUNT(*) FROM UserDetails ud WHERE ud.email = :email")
    int getCountByEmail(String email);

    @Query("SELECT COUNT(*) FROM UserDetails ud WHERE ud.employeeId = :employeeId")
    int getCountByEmployeeId(Integer employeeId);

    UserDetails findByUserId(Integer userId);
}