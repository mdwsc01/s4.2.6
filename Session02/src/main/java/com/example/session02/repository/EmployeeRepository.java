package com.example.session02.repository;

import com.example.session02.model.EmployeeFilter;
import com.example.session02.model.entity.Employee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(
                1L, "Nguyễn Văn A", "a@gmail.com", "IT"));

        employees.add(new Employee(
                2L, "Trần Thị B", "b@gmail.com", "HR"));

        employees.add(new Employee(
                3L, "Lê Văn C", "c@gmail.com", "Marketing"));

        employees.add(new Employee(
                4L, "Trương Nhân D", "d@gmail.com", "Sales"));

        employees.add(new Employee(
                5L, "Phạm Thu E", "e@gmail.com", "IT"));
    }

    // Lấy tất cả nhân viên
    public synchronized List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    // Tìm nhân viên theo ID
    public synchronized Employee findById(Long id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Tìm nhân viên theo tên
    public synchronized List<Employee> findByName(String name) {
        String keyword = name.toLowerCase(Locale.ROOT);

        return employees.stream()
                .filter(e -> e.getFullName()
                        .toLowerCase(Locale.ROOT)
                        .contains(keyword))
                .toList();
    }

    // Lọc theo tên và phòng ban
    public synchronized List<Employee> findByFilter(
            EmployeeFilter filter) {

        String name = filter.getName();
        String department = filter.getDepartment();

        return employees.stream()
                .filter(e -> name == null
                        || e.getFullName().toLowerCase(Locale.ROOT)
                        .contains(name.toLowerCase(Locale.ROOT)))
                .filter(e -> department == null
                        || e.getDepartment()
                        .equalsIgnoreCase(department))
                .toList();
    }

    // Thêm nhân viên
    public synchronized Employee save(Employee employee) {
        if (findById(employee.getId()) != null) {
            throw new IllegalStateException(
                    "ID nhân viên đã tồn tại");
        }

        employees.add(employee);
        return employee;
    }

    // Xóa nhân viên theo ID
    public synchronized boolean deleteById(Long id) {
        return employees.removeIf(e -> e.getId().equals(id));
    }
}