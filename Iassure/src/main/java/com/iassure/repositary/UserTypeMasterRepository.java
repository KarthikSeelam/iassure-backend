package com.iassure.repositary;

import com.iassure.entity.UserTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeMasterRepository extends JpaRepository<UserTypeMaster, Long>, JpaSpecificationExecutor<UserTypeMaster> {

}