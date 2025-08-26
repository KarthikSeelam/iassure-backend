package com.iassure.usermanagement.repository;

import com.iassure.usermanagement.entity.UserDetailsById;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsByIdRepo extends JpaRepository<UserDetailsById,Integer> {
    @Query(value = "SELECT U.UserID,FirstName,LastName,ContactNumber,UserName,(U.Gender) GenderID,(MS.SourceType) GenderName,(U.Title) TitleID, (MS1.SourceType) TitleName,U.UserTypeID,UT.UserType,U.DepartmentID,DM.DepartmentName from Users U INNER JOIN MasterSource MS ON MS.SourceId = U.Gender AND MS.SourceName = 'Gender' INNER JOIN MasterSource MS1 ON MS1.SourceId = U.Title AND MS1.SourceName = 'Title' INNER JOIN UserTypes UT ON UT.UserTypeID = U.UserTypeID\n" +
            "INNER JOIN DepartmentMaster DM ON DM.DepartmentID = U.DepartmentID WHERE U.UserID = :userId",nativeQuery = true)
    UserDetailsById getUserDetailsById(@Param("userId") Integer userId);
}
