package br.com.bank.core.services;

import br.com.bank.core.entity.Account;
import br.com.bank.core.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Account not found")));
    }

    @Override
    public Mono<Account> save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> delete(Account account) {
        return accountRepository.delete(account);
    }

    public Flux<Account> findByBranchAndAccountNumber(Account accountFilter){
        return accountRepository.findByBranchAndAccountNumber(accountFilter);
    }

    public Mono<Account> getCurrentBalance(Account accountFilter){
        return accountRepository.findByBranchAndAccountNumber(accountFilter).next();
    }


}
