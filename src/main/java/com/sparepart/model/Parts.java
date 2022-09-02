package com.sparepart.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="part")
@AllArgsConstructor
@NoArgsConstructor
public class Parts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int partId;
	
	@NotNull(message="Please provide name")
	@NotBlank(message="Name is mandatory")
	@Pattern(regexp = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)*\\s*$", message = "Wrong part name")
	private String partName;
	
	@NotNull(message="Please provide description")
	@NotBlank(message="Description is mandatory")
	private String partDesc;
	
	@NotNull(message="Please provide Name")
	@Range(min= 0, message = "Please provide correct cost")
	private double partCost;
	
	@NonNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "machineId", referencedColumnName = "machineId")
	private Machine partMachineId;
}
