package com.minj.checkYourGrades.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="users")
public class User {
	
	@NotNull
	private String username;
	
	@Id
	private int password;
	
	@OneToOne(optional=false, cascade=CascadeType.ALL)
	@JoinColumn(unique=true)
	private Grade grade;	
	
	
	@NotNull
	private boolean enabled = false;
	
	@NotNull
	private String authority;
	
	private boolean checked = false;

}
