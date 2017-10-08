package com.kpi.diploma.smartroads.repository;

import com.kpi.diploma.smartroads.model.document.user.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
}
