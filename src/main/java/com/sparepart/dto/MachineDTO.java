package com.sparepart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineDTO {
	private int machineId;
	private String machineName;
	private String machineDesc;

	private int machineTypeId;	
	private int companyId;
}
