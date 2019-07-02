package br.com.bank.core.entity;

import br.com.bank.core.enums.EPersonType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class Person {

    @Id
    private ObjectId id;

    @NotNull
    private EPersonType personType;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public EPersonType getPersonType() {
        return personType;
    }

    public void setPersonType(EPersonType personType) {
        this.personType = personType;
    }

}
