package com.jrp.pma.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {
	
	@Id
    @SequenceGenerator(name="project_seq", sequenceName = "project_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
	private long projectId;
	
	@NotBlank(message="This field cant be empty!")
	@Size(max=50)
	private String name;
	
	@NotBlank(message="This field cant be empty!")
	private String stage; //NOTSTARTED,COMPLETED,INPROGRESS
	
	@NotBlank(message="This field cant be empty!")
	@Size(max=200)
	private String description;
	
	@NotNull(message="This field cant be empty")
	@DateTimeFormat(pattern="dd-mm-yyyy")
	private Date startDate;
	
	@NotNull(message="This field cant be empty")
	@DateTimeFormat(pattern="dd-mm-yyyy")
	private Date endDate;
	
	@ManyToMany(cascade= {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},
	           fetch=FetchType.LAZY)
	@JoinTable(name="project_employee",
	            joinColumns=@JoinColumn(name="project_id"),
	            inverseJoinColumns=@JoinColumn(name="employee_id"))
	
	@JsonIgnore
	private List<Employee> employees;
	
	public Project() {
	
	}
	public Project(String name, String stage, String description) {
		super();
		this.name = name;
		this.stage = stage;
		this.description = description;
		
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void addEmployee(Employee emp) {
		if(employees==null) {
			employees=new ArrayList<>();
		}
		employees.add(emp);
		
	}

}
