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
public class WalletModel {
    private String id;

    @NotBlank(message="Parameter cannot be null or empty")
    private String documentType;

    @NotBlank(message="Name cannot be null or empty")
    private String documentNumber;

    @NotBlank(message="Name cannot be null or empty")
    private String imeiCellphone;

    @NotBlank(message="Name cannot be null or empty")
    private String email;

    private String debitCardId;
}
