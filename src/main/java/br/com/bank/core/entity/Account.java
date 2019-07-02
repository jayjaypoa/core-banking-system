package br.com.bank.core.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Document(collection = "account")
public class Account {

    @Id
    private ObjectId id;

    @NotNull
    private NaturalPerson naturalPerson;

    @NotNull
    private String branchNumber;

    @NotNull
    private String accountNumber;

    private BigDecimal balance;

    public Account(ObjectId id, NaturalPerson naturalPerson, String branchNumber, String accountNumber, BigDecimal balance) {
        this.id = id;
        this.naturalPerson = naturalPerson;
        this.branchNumber = branchNumber;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public NaturalPerson getNaturalPerson() {
        return naturalPerson;
    }

    public void setNaturalPerson(NaturalPerson naturalPerson) {
        this.naturalPerson = naturalPerson;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id) &&
                naturalPerson.equals(account.naturalPerson) &&
                branchNumber.equals(account.branchNumber) &&
                accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naturalPerson, branchNumber, accountNumber);
    }

}
