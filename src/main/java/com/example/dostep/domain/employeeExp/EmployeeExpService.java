package com.example.dostep.domain.employeeExp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeExpService {
    private final EmployeeExpRepository employeeExpRepository;
}
