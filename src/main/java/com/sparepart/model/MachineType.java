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
@Table(name="machine_type")
@AllArgsConstructor
@NoArgsConstructor
public class MachineType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int machineTypeId;
	
	@NotNull(message="Please provide name")
	@NotBlank(message="Please provide name")
	@Pattern(regexp = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)*\\s*$", message = "Wrong machine type name")
	private String machineTypeName;
	
	@NotNull(message="Please provide description")
	private String machineTypeDesc;
}
