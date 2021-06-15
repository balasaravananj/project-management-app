package com.jrp.pma.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrp.pma.entity.Employee;
import com.jrp.pma.services.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
    @Autowired
	EmployeeService empServ;
    
    @GetMapping
	public String displayEmployees(Model model) {
		Iterable<Employee> employees=empServ.getAll();
		model.addAttribute("employeesList", employees);
		return "employees/view-employees";
		
	}
	
	@GetMapping("/new")
	public String displayEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return"employees/employee-form";
	}
	
	
	@PostMapping("/save")
	public String createEmployeeForm(Model model,@Valid Employee employee,Errors error) {
		
		System.out.println(employee.getProjects());
		if(error.hasErrors()) {
			System.out.println(error.getErrorCount());
			return "employees/employee-form";
		}
		empServ.save(employee);
		return "redirect:/employees";
	}
	
	@GetMapping("/update")
	public String updateEmployee(@RequestParam("id") long id,Model model) {
		
		Employee employee = empServ.findByEmployeeId(id);
		System.out.println(employee.getProjects());
		model.addAttribute("employee", employee);
		return "employees/employee-form";
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("id") long id,Model model) {
		empServ.deleteEmployee(id);
		return "redirect:/employees";
	}
	
}
