package com.iassure.usermanagement.repository;

import com.iassure.usermanagement.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Integer> {
    Optional<UsersEntity> findByUsername(String email);

    @Modifying
    @Query(value = "UPDATE Users SET IsActive = 0 WHERE UserID = :userId",nativeQuery = true)
    void deleteUser(int userId);
}
