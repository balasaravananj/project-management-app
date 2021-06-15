package com.jrp.pma.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.entity.Project;

@RestController
@RequestMapping("/app-api/projects")
public class ProjectApiController {

	@Autowired
	ProjectRepository proRepo;
	
	@GetMapping
	public Iterable<Project> getProjects(){
		return proRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Project getProjectById(@PathVariable("id") long id) {
		return proRepo.findById(id).get();
	}
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Project create(@RequestBody @Valid Project project) {
	  return proRepo.save(project);	
	}
	
	@PutMapping(path="/{id}",consumes="application/json")
	@ResponseStatus(HttpStatus.OK)
	public Project update(@RequestBody @Valid Project project) {
		return proRepo.save(project);
	}
	
	@PatchMapping(path="{id}",consumes="application/json")
	@ResponseStatus(HttpStatus.OK)
	public Project partialUpdate(@PathVariable("id") long id,@RequestBody @Valid Project patchProject) {
		Project project = proRepo.findById(id).get();
		
		if(patchProject.getName() != null) {
			project.setName(patchProject.getName());
		}
		if(patchProject.getDescription() != null) {
			project.setDescription(patchProject.getDescription());
		}
		if(patchProject.getStage() != null) {
			project.setStage(patchProject.getStage());
		}
		
		return proRepo.save(project);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") long id) {
		try {
		proRepo.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
		}
	}
	
	@GetMapping(params={"page","size"})
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Project> findPaginatedEmployees(@RequestParam("page") int page,@RequestParam("size") int size){
		Pageable pageAndSize =PageRequest.of(page, size);
		
		return proRepo.findAll(pageAndSize);
	}
}
