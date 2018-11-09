package com.troila.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.troila.model.PolicyExternal;

@Component
public interface PolicyExternalRepository extends JpaRepository<PolicyExternal, String>{

}
