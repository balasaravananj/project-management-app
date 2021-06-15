package com.jrp.pma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.dto.ChartData;
import com.jrp.pma.dto.TimeChartData;
import com.jrp.pma.entity.Project;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository proRepo;
	
	public Project save(Project project) {
		return proRepo.save(project);
	}
	
	public Iterable<Project> getAll(){
		return proRepo.findAll();
	}
	
	public List<ChartData> getProjectStage(){
		return proRepo.getProjectStatus();
	}

	public Project findProjectById(long id) {
		return proRepo.findByProjectId(id);
	}

	public void deleteProject(long id) {
		proRepo.deleteById(id);
		
	}

	public List<TimeChartData> getTimeData() {
	   return proRepo.getTimeData();
	}

}
