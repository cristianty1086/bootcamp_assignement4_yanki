package com.nttdata.bootcamp.assignement4.msyanki.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletPaymentModel {
    private String id;

    @NotBlank(message="Parameter cannot be null or empty")
    private String walletId;

    @NotBlank(message="Name cannot be null or empty")
    private double amount;

    @NotBlank(message="Name cannot be null or empty")
    private String currency;

    @NotBlank(message="Name cannot be null or empty")
    private String typePayment;

    private String createdAt;
}
