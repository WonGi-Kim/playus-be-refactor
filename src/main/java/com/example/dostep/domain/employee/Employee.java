package com.example.dostep.domain.employee;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "employee")
public class Employee {
}
