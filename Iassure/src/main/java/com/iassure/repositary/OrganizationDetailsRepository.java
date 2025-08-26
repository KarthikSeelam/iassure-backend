package com.iassure.repositary;

import com.iassure.entity.OrganizationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationDetailsRepository extends JpaRepository<OrganizationDetails, Integer>, JpaSpecificationExecutor<OrganizationDetails> {

    OrganizationDetails findByOrganizationId(Integer orgId);
}