package com.sparepart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartsDTO {
	private int partId;
	private String partName;
	private String partDesc;
	private double partCost;
	
	private int partMachineId; //for laptop or computer
}
