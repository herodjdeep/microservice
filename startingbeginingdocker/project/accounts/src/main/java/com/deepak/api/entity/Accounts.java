package com.deepak.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accounts extends BaseEntity {
	
	private int customerId;
	@Id
	private long accountNumber;
	private String accountType;
	private String branchAddress;
	

}

