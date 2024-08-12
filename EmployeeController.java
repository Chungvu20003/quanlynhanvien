package com.example.quanlynhanvien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee/list";
    }


    @PostMapping ("/new")
    public String createEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/form";
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String editEmployeeForm(@PathVariable("id") Long id, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employee/form";
        } else {
            model.addAttribute("errorMessage", "Employee not found");
            return "employee/list";
        }
    }


    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable("id") Long id, @ModelAttribute Employee employee) {
        employee.setId(id);
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }


}

