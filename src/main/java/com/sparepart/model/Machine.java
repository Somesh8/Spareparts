package com.sparepart.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="machine")
@AllArgsConstructor
@NoArgsConstructor
public class Machine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int machineId;
	
	@NotNull(message="Please provide name")
	@NotBlank(message="Please provide name")
	@Pattern(regexp = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)*\\s*$", message = "Wrong machine name")
	private String machineName;
	
	@NotNull(message="Please provide description")
	@NotBlank(message="Description is mandatory")
	private String machineDesc;
	
	@NotNull(message="Please provide machine type")
	@OneToOne
	private MachineType machineType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "companyId", referencedColumnName = "companyId")
	private Company company;
}
