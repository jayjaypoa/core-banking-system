package br.com.bank.core.data;

import br.com.bank.core.entity.Account;
import br.com.bank.core.entity.CompanyPerson;
import br.com.bank.core.entity.NaturalPerson;
import br.com.bank.core.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Component
public class AccountDummyData implements CommandLineRunner{

    private final AccountRepository accountRepository;

    AccountDummyData(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        accountRepository.deleteAll()
                .thenMany(
                        Flux.just("Nicolas Cage", "Brad Pitt", "Adam Sandler", "Julia Roberts", "Tom Cruise")
                                .map(name -> new Account(
                                        UUID.randomUUID().toString(),
                                        new NaturalPerson(
                                                UUID.randomUUID().toString(),
                                                String.valueOf(new Random().nextInt(1000000)),
                                                name),
                                        String.valueOf(new Random().nextInt(1000)),
                                        String.valueOf(new Random().nextInt(5000)),
                                        new BigDecimal(1000)))
                                .flatMap(accountRepository::save))
                .thenMany(
                        Flux.just("Google", "Facebook", "Oracle", "Dell", "SAP")
                                .map(name -> new Account(
                                        UUID.randomUUID().toString(),
                                        new CompanyPerson(
                                                UUID.randomUUID().toString(),
                                                String.valueOf(new Random().nextInt(90000)*1000),
                                                name),
                                        String.valueOf(new Random().nextInt(2000)),
                                        String.valueOf(new Random().nextInt(9000)),
                                        new BigDecimal(50000)))
                                .flatMap(accountRepository::save))
                .subscribe(System.out::println);
    }


}