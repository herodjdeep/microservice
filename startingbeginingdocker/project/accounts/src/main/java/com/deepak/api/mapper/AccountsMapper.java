package com.deepak.api.mapper;

import com.deepak.api.dto.AccountsDto;
import com.deepak.api.entity.Accounts;

public class AccountsMapper {
	
	public static AccountsDto mapToAccountsDto(Accounts accounts,AccountsDto accountsDto) {
		accountsDto.setAccountNumber(accounts.getAccountNumber());
		accountsDto.setAccountType(accounts.getAccountType());
		accountsDto.setBranchAddress(accounts.getBranchAddress());
		return accountsDto;
		
	}
	
	public static Accounts mapToAccounts(AccountsDto accountDto, Accounts accounts) {
		
		accounts.setAccountNumber(accountDto.getAccountNumber());
		accounts.setAccountType(accountDto.getAccountType());
		accounts.setBranchAddress(accountDto.getBranchAddress());
		return accounts;
	}

}
