package com.example.dostep.domain.employeeExp;

import com.example.dostep.domain.employeeExp.model.EmployeeExp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeExpRepository extends MongoRepository<EmployeeExp, String> {
    void deleteByYear(int year);
}
