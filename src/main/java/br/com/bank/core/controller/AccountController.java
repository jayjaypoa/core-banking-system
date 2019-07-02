package br.com.bank.core.controller;

import br.com.bank.core.entity.Account;
import br.com.bank.core.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value="/account")
    private Flux<Account> getAccount(){
        return accountService.findAll();
    }

    @GetMapping(value="/account/{id}")
    private Mono<Account> getAccountById(String id){
        return accountService.findById(id);
    }

    @PostMapping(value="/account")
    private Mono<Account> save(Account account){
        return accountService.save(account);
    }

}
