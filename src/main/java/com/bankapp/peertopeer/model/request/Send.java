package com.bankapp.peertopeer.model.request;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Send extends Credential{
    @NotNull(message = "withdrawnAmount required")
    @DecimalMin(value = "1.0", message = "1.0 is the minimum")
    private Double sendAmount;
    @NotBlank(message = "beneficiaryAccountNumber is mandatory")
    private String beneficiaryAccountNumber;

    public String getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public Double getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(Double sendAmount) {
        this.sendAmount = sendAmount;
    }
}
