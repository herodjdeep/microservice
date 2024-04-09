package com.deepak.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDto {
	
	@Pattern(regexp = "\\d{10}", message = "Account number must be 10 digits")
	@NotEmpty(message ="account number can not be null or empty")
	private long accountNumber;
	
	@NotEmpty(message ="account type can not be null or empty")
	private String accountType;
	
	@NotEmpty(message ="branch address can not be null or empty")
	private String branchAddress;

}
