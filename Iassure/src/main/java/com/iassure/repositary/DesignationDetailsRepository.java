package com.iassure.repositary;

import com.iassure.entity.DesignationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Sravanth T
 */
@Repository
public interface DesignationDetailsRepository extends JpaRepository<DesignationDetails, Integer> {

    List<DesignationDetails> findByOrganizationId(Integer organizationId);
}