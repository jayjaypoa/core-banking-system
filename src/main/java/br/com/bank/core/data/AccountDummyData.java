package br.com.bank.core.data;

import br.com.bank.core.entity.Account;
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
                        Flux.just( "Nicolas Cage", "Brad Pitt", "Adam Sandler", "Julia Roberts", "Tom Cruise",
                                   "Will Smith", "Nicole Kidman", "Angelina Jolie", "Sylvester Stallone",
                                   "Leonardo DiCaprio" )
                                .map(name -> new Account(
                                        UUID.randomUUID().toString(),
                                        String.valueOf(new Random().nextInt(1000)),
                                        String.valueOf(new Random().nextInt(5000)),
                                        new BigDecimal(1000)))
                                .flatMap(accountRepository::save))
                .subscribe(elem -> System.out.println(elem.toString()));

    }


}