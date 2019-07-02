package br.com.bank.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Document
public class Account {

    @Id
    private String id;

    @NotNull
    private Person person;

    @NotNull
    private String branchNumber;

    @NotNull
    private String accountNumber;

    private BigDecimal balance;

    public Account( String id,
                    @NotNull Person person,
                    @NotNull String branchNumber,
                    @NotNull String accountNumber,
                    BigDecimal balance) {
        this.id = id;
        this.person = person;
        this.branchNumber = branchNumber;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
                person.equals(account.person) &&
                branchNumber.equals(account.branchNumber) &&
                accountNumber.equals(account.accountNumber) &&
                Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, branchNumber, accountNumber, balance);
    }

}
