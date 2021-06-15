package com.jrp.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrp.pma.dto.ChartData;
import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entity.Employee;
import com.jrp.pma.entity.Project;
import com.jrp.pma.services.EmployeeService;
import com.jrp.pma.services.ProjectService;

@Controller
public class HomeController {
	
	@Value("${version}")
	private String ver;

	@Autowired
	ProjectService proServ;
	
	@Autowired
	EmployeeService empServ;
	
	@GetMapping("/")
	public String displayHome(Model model) throws JsonProcessingException {
		
		model.addAttribute("envVersionNum",ver);
		
		Iterable<Project> projects= proServ.getAll();
		List<EmployeeProject> empProject = empServ.employeeProjects();
		List<ChartData> projectData = proServ.getProjectStage();
		Iterable<Employee> employees = empServ.getAll();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString=objectMapper.writeValueAsString(projectData);
		
		model.addAttribute("employeesList",employees);
		model.addAttribute("projectStatusCnt", jsonString);                
		model.addAttribute("projectsList", projects);
		model.addAttribute("employeeProject", empProject);
		return"main/home";
	}
	
   @GetMapping("/info")
   public String displayProjectInfo(@RequestParam("id") long id,Model model) {
	   
	   Employee employee = empServ.findByEmployeeId(id);
	   List<Project> projects = employee.getProjects();
	   model.addAttribute("projects", projects);
	   return "projects/project-info";
   }
}
