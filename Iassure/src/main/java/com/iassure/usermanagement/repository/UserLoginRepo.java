package com.iassure.usermanagement.repository;

import com.iassure.usermanagement.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepo extends JpaRepository<UserLogin,Integer> {
    @Procedure(procedureName = "USP_GetLoginDetails")
    UserLogin getLoginDetails(String username,String password);
    @Modifying
    @Query(value = "UPDATE Users SET Password = :password,LoginFirstTime = 0 WHERE UserName = :username",nativeQuery = true)
    void forgotPassword(String password,String username);
}
