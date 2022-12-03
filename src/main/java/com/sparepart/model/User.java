package com.sparepart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@NotNull(message="Please provide name")
	@NotBlank(message="Please provide name")
	@Pattern(regexp = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)*\\s*$", message = "Wrong user name")
	private String fullname;
	
	@NotNull(message="Please provide email")
	private String email;
	
	@NotNull(message="Please provide password")
	private String password;
	
	@NotNull(message="Please provide age")
	private int age;
}
