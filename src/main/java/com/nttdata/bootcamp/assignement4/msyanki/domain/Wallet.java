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
public class Wallet {

	@Id
	private String id;

	@NotNull
	private String documentType;

	@NotNull
	private String documentNumber;

	@NotNull
	private String imeiCellphone;

	@NotNull
	private String email;

	private String debitCardId;
}
