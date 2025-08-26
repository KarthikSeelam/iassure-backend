package com.iassure.incident.repository;

import com.iassure.incident.entity.Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FieldsRepository extends JpaRepository<Fields,Integer> {

    @Query(value = "SELECT * From fields WHERE is_active = 1",nativeQuery = true)
    List<Fields> getAllFields();

    @Modifying
    @Transactional
    @Query(value = "UPDATE fields SET is_active = 0 WHERE id = :id",nativeQuery = true)
    void deleteField(int id);
}
