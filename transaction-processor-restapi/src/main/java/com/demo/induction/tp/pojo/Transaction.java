package com.demo.induction.tp.pojo;

import java.math.BigDecimal;


public class Transaction {
    private String type;
    private BigDecimal amount;
    private String narration;

    public Transaction() {}

    public Transaction(String type, BigDecimal amount, String narration) {
        this.type = type;
        this.amount = amount;
        this.narration = narration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}
