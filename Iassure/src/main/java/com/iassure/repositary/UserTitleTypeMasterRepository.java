package com.iassure.repositary;

import com.iassure.entity.UserTitleTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sravanth T
 */
@Repository
public interface UserTitleTypeMasterRepository extends JpaRepository<UserTitleTypeMaster,Integer> {

}