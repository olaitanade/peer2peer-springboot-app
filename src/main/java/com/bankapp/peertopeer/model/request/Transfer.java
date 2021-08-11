package com.bankapp.peertopeer.model.request;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Transfer extends Credential{
    @NotNull(message = "withdrawnAmount required")
    @DecimalMin(value = "1.0", message = "1.0 is the minimum")
    private Double transferAmount;
    @NotBlank(message = "beneficiaryAccountNumber is mandatory")
    private String beneficiaryAccountNumber;

    @NotBlank(message = "bank is mandatory")
    private String bank;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public Double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Double transferAmount) {
        this.transferAmount = transferAmount;
    }
}
