package com.jrp.pma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jrp.pma.dto.ChartData;
import com.jrp.pma.dto.TimeChartData;
import com.jrp.pma.entity.Project;

@RepositoryRestResource(collectionResourceRel="apiprojects",path="apiprojects")
public interface ProjectRepository extends PagingAndSortingRepository<Project,Long> {

	
	@Query(nativeQuery=true,value="SELECT stage as label, COUNT(*) as value "
			+ "FROM project "
			+ "GROUP BY stage")
	public List<ChartData> getProjectStatus();

	public Project findByProjectId(long id);
	
	
	@Query(nativeQuery=true, value="SELECT name as projectName, start_date as startDate, end_date as endDate"
			+ " FROM project WHERE start_date is not null")
	public List<TimeChartData> getTimeData();
}
