package br.com.bank.core;

import br.com.bank.core.entity.Account;
import br.com.bank.core.entity.NaturalPerson;
import br.com.bank.core.repository.AccountRepository;
import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class DummyData implements CommandLineRunner {

    private final AccountRepository accountRepository;

    public DummyData(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        accountRepository.deleteAll()
                .thenMany(
                        Flux.just("Nicolas Cage", "Brad Pitt", "Adam Sandler", "Julia Roberts", "Tom Cruise")
                                .map(name ->
                                        new Account(
                                                new ObjectId(UUID.randomUUID().toString()),
                                                new NaturalPerson(new ObjectId(UUID.randomUUID().toString()),
                                                        UUID.randomUUID().toString(), name),
                                                "0001",
                                                "111111",
                                                new BigDecimal(5000)))
                                .flatMap(accountRepository::save))
                .subscribe(System.out::println);

    }

}
