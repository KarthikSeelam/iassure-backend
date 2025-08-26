package com.iassure.repositary;

import com.iassure.entity.UserWorkProfileMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserWorkProfileMappingRepository extends JpaRepository<UserWorkProfileMapping, Long>, JpaSpecificationExecutor<UserWorkProfileMapping> {

    List<UserWorkProfileMapping> findByFkUserDetailsId(Integer userId);
}