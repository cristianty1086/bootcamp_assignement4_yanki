package com.nttdata.bootcamp.assignement4.msyanki.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "movements")
public class WalletPayment {
	
	@Id
    private String id;
		
	@NotNull
    private String walletId;
	
	@NotNull
	private double amount;
	
	@NotNull	
	private String currency;
	
	@NotNull		
	private String typePayment;

	private String createdAt;
}
