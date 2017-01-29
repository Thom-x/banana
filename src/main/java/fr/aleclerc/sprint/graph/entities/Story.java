package fr.aleclerc.sprint.graph.entities;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class Story {
	@Id 
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	private UUID id;
	@Column(unique=true) 
	@NotNull
	private String name;
	@Column
	private Float complexity;
	@Column
	private Instant addDate;
	@Column
	private Instant closeDate;
	@Column
	private Float businessValue;
	@Column
    @Enumerated(EnumType.STRING)
	private EStoryType type;
	
	public Story(){

	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Float getComplexity() {
		return complexity;
	}
	public void setComplexity(Float complexity) {
		this.complexity = complexity;
	}
	public Instant getAddDate() {
		return addDate;
	}
	public void setAddDate(Instant addDate) {
		this.addDate = addDate;
	}
	public Instant getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Instant closeDate) {
		this.closeDate = closeDate;
	}


	public Float getBusinessValue() {
		return businessValue;
	}


	public void setBusinessValue(Float businessValue) {
		this.businessValue = businessValue;
	}


	public EStoryType getType() {
		return type;
	}


	public void setType(EStoryType type) {
		this.type = type;
	}


	@Override
	public String toString() {
		return "Story [id=" + id + ", name=" + name + ", complexity=" + complexity + ", addDate=" + addDate + ", closeDate=" + closeDate + "]";
	}


	
}
