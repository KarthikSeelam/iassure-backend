package com.iassure.usermanagement.repository;

import com.iassure.usermanagement.entity.DepartmentUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentUserRepository extends JpaRepository<DepartmentUserMapping,Integer> {
    Optional<DepartmentUserMapping> findByUserId(Integer userId);
}
