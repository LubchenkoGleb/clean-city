package com.kpi.diploma.smartroads.repository;

import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.document.user.Role;
import com.kpi.diploma.smartroads.model.document.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {

    User findByEmail(String email);

    List<Company> findAllByRolesContains(Role role);
}
