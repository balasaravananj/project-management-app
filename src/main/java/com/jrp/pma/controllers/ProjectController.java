package com.jrp.pma.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrp.pma.dto.TimeChartData;
import com.jrp.pma.entity.Employee;
import com.jrp.pma.entity.Project;
import com.jrp.pma.services.EmployeeService;
import com.jrp.pma.services.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	ProjectService proServ;
	
	@Autowired
	EmployeeService empServ;
	
	
	@GetMapping
	public String displayProjects(Model model) {
		Iterable<Project> projects=proServ.getAll();
		model.addAttribute("projectsList", projects);
		return "projects/view-projects";
		
	}
	
	@GetMapping("/new")
	public String displayProjectForm(Model model) {
		
		Project aProject = new Project();
		model.addAttribute("project", aProject);
		
		Iterable<Employee> employees=empServ.getAll();
		model.addAttribute("allEmployees", employees);
		
		return "projects/new-project";
		
	}
	
	@PostMapping("/save")
	public String createProjectForm(Model model,@Valid Project project,Errors errors) {
		
		if(errors.hasErrors()) {
			Iterable<Employee> employees = empServ.getAll();
	     	model.addAttribute("allEmployees", employees);
			return "projects/new-project";
		}		
		proServ.save(project);
		//use a redirect to prevent duplicate submission...
		return "redirect:/projects";
	}
	
	@GetMapping("/update")
	public String updateProject(@RequestParam("id") long id,Model model) {
		Iterable<Employee> employees = empServ.getAll();
		Project project = proServ.findProjectById(id);
		model.addAttribute("project",project);
		model.addAttribute("allEmployees", employees);
		return "projects/new-project";
	}
	
	@GetMapping("/delete")
	public String deleteProject(@RequestParam("id") long id,Model model) {
		proServ.deleteProject(id);
		return "redirect:/projects";
	}
	
	@GetMapping("/timelines")
	public String displayProjectTimelines(Model model) throws JsonProcessingException {
		
		List<TimeChartData> timelineData = proServ.getTimeData();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonTimelineString = objectMapper.writeValueAsString(timelineData);
		
		model.addAttribute("projectTimeList", jsonTimelineString);
		
		return "projects/project-timelines";
	}
	
	

}
