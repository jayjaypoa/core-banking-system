package br.com.bank.core.services.implementation;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.entity.Account;
import br.com.bank.core.enums.EValidationResponse;
import br.com.bank.core.exceptions.CoreException;
import br.com.bank.core.repository.AccountRepository;
import br.com.bank.core.services.IAccountService;
import br.com.bank.core.validations.AccountValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountValidation accountValidation;

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new CoreException("Account not found")));
    }

    @Override
    public Mono<Account> save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> delete(Account account) {
        return accountRepository.delete(account);
    }

    public Mono<Account> getCurrentBalance(Account accountFilter){
        logger.info("Current balance executing : " + accountFilter.toString());
        return accountRepository.findByBranchAndAccountNumber(accountFilter)
                .switchIfEmpty(Mono.error(new CoreException("Account not found for check the current balance")));
    }

    public Mono<Account> verifyAccountExistence(Account account){

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ACCOUNT_NOT_FOUND);

        accountValidation.validate(account)
            .flatMap(accountRepository::findByBranchAndAccountNumber)
            .flatMap(obj -> {
                return accountValidation.validateAll(obj);
            })
            .onErrorResume(obj -> {
                return Mono.error(new CoreException(apiErrorResponse));
            });

        return Mono.error(new CoreException(apiErrorResponse));
    }

}
