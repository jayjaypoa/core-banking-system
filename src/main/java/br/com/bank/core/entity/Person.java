package br.com.bank.core.entity;

import br.com.bank.core.enums.EPersonType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Person {

    @Id
    @NotNull
    private String id;

    @NotNull
    private EPersonType personType;

    public Person(@NotNull String id, @NotNull EPersonType personType) {
        this.id = id;
        this.personType = personType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EPersonType getPersonType() {
        return personType;
    }

    public void setPersonType(EPersonType personType) {
        this.personType = personType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id) &&
                personType == person.personType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personType);
    }

}
