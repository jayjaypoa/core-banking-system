package br.com.bank.core.entity;

import br.com.bank.core.enums.EOperationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Document
public class Operation {

    @Id
    @NotNull
    private String id;

    @NotNull
    private EOperationType operationType;

    @NotNull
    private Account account;

    @NotNull
    private Transaction transaction;

    public Operation(@NotNull String id,
                     @NotNull EOperationType operationType,
                     @NotNull Account account,
                     @NotNull Transaction transaction) {
        this.id = id;
        this.operationType = operationType;
        this.account = account;
        this.transaction = transaction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(EOperationType operationType) {
        this.operationType = operationType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return id.equals(operation.id) &&
                operationType == operation.operationType &&
                account.equals(operation.account) &&
                transaction.equals(operation.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operationType, account, transaction);
    }

}
