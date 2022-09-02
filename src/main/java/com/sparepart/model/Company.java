package com.sparepart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="company")
@AllArgsConstructor
@NoArgsConstructor
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int companyId;
	
	@NotNull(message="Please provide name")
	@NotBlank(message="Please provide name")
	@Size(min = 1, max=15, message = "Out of min-max boundary")
	@Pattern(regexp = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)*\\s*$", message = "Wrong company name")
	private String companyName;
	
	@NotNull(message="Please provide description")
	@NotBlank(message="Description is mandatory")
	private String companyDesc;

}
