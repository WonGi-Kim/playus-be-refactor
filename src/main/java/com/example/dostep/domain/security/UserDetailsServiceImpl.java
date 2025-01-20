package com.example.dostep.domain.security;


import com.example.dostep.domain.employee.model.Employee;
import com.example.dostep.domain.employee.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public UserDetailsServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByAccountUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("해당 사용자가 존재하지 않습니다: " + username)
        );

        return new UserDetailsImpl(employee);
    }
}
