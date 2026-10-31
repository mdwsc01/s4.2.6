package com.example.session02.service;

import com.example.session02.model.EmployeeFilter;
import com.example.session02.model.entity.Employee;
import com.example.session02.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Lấy danh sách
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Lấy nhân viên theo ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Tìm theo tên
    public List<Employee> searchByName(String name) {
        return employeeRepository.findByName(name);
    }

    // Tìm kiếm nâng cao
    public List<Employee> searchByFilter(EmployeeFilter filter) {
        return employeeRepository.findByFilter(filter);
    }

    // Thêm nhân viên
    public Employee addEmployee(Employee employee) {

        if (employee == null
                || employee.getId() == null
                || employee.getId() <= 0
                || employee.getFullName() == null
                || employee.getFullName().isBlank()
                || employee.getEmail() == null
                || employee.getEmail().isBlank()
                || employee.getDepartment() == null
                || employee.getDepartment().isBlank()) {

            throw new IllegalArgumentException(
                    "Thông tin nhân viên không hợp lệ");
        }

        return employeeRepository.save(employee);
    }

    // Xóa nhân viên
    public void deleteEmployee(Long id) {

        boolean deleted = employeeRepository.deleteById(id);

        if (!deleted) {
            throw new NoSuchElementException(
                    "Không tìm thấy nhân viên với ID: " + id);
        }
    }
}