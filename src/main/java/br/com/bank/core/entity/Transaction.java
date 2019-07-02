package br.com.bank.core.entity;

import br.com.bank.core.enums.ETransactionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Document
public class Transaction {

    @Id
    @NotNull
    private String id;

    @NotNull
    private ETransactionType transactionType;

    @NotNull
    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal amount;

    public Transaction(@NotNull String id,
                       @NotNull ETransactionType transactionType,
                       @NotNull BigDecimal amount) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ETransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(ETransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id) &&
                transactionType == that.transactionType &&
                amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType, amount);
    }

}
