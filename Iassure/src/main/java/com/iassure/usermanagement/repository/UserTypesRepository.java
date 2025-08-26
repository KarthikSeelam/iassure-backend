package com.iassure.usermanagement.repository;

import com.iassure.usermanagement.entity.UserTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypesRepository extends JpaRepository<UserTypes,Integer> {
}
