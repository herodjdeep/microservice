package com.deepak.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
	
	@NotEmpty(message="name can not be null or empty")
	@Size(min=5, max=30,message="The length of the customer name should be between 5 and 30")
	private String name;
	
	@NotEmpty(message ="email can not be null or empty")
	@Email(message="email address should be a valid value")
	private String email;
	
	@Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
	@NotEmpty(message ="mobile number can not be null or empty")
	private String mobileNumber;
	
	private AccountsDto accountsDto;
	

}
