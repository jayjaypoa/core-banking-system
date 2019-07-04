package br.com.bank.core.services.implementation;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.entity.Account;
import br.com.bank.core.entity.Transaction;
import br.com.bank.core.enums.ETransactionType;
import br.com.bank.core.enums.EValidationResponse;
import br.com.bank.core.exceptions.CoreException;
import br.com.bank.core.repository.AccountRepository;
import br.com.bank.core.repository.TransactionRepository;
import br.com.bank.core.services.ITransactionService;
import br.com.bank.core.validations.TransactionValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class TransactionService implements ITransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private static final String TOPIC = "Kafka_Topic";

   //  @Autowired
   //private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionValidation transactionValidation;

    @Autowired
    private AccountService accountService;

    @Override
    public Mono<Transaction> executeTransaction(Transaction transaction) {

        logger.debug("Processing a new transaction");
        logger.debug("Transaction type : " + transaction.getTransactionType().getOperationType());

        return transactionValidation
                .validate(transaction)
                .flatMap(t -> accountService.verifyAccountExistence(t.getAccount()))
                .flatMap(ac -> {
                    logger.debug("Account exists. Updating transaction information...");
                    transaction.setAccount(ac);
                    return Mono.just(transaction);
                })
                .flatMap(transactionRepository::save)
                .flatMap(t -> {
                    if(t.getTransactionType().equals(ETransactionType.CREDIT)){
                        return this.sensibilizeAccountWithCreditOperation(t);
                    } else {
                        return this.sensibilizeAccountWithDebitOperation(t);
                    }
                })
                //.flatMap(kafkaTemplate.send(TOPIC, transaction))
                .onErrorResume(error -> {
                    logger.error("[ERROR] Executing credit transaction : " + error.getMessage());
                    return Mono.error(
                            new CoreException(
                                    new ApiErrorResponse(EValidationResponse.TRANSACTION_NOT_EFETIVATED)));
                });

    }

    private Mono<Transaction> sensibilizeAccountWithCreditOperation(Transaction transaction){
        return sensibilizeAccountBalance(transaction);
    }

    private Mono<Transaction> sensibilizeAccountWithDebitOperation(Transaction transaction){
        return this.verifyEnoughtMoneyForDebitTransaction(transaction)
                .flatMap(this::sensibilizeAccountBalance);
    }

    private Mono<Transaction> sensibilizeAccountBalance(Transaction transaction){
        Account affectedAccount = transaction.getAccount();
        if(transaction.equals(ETransactionType.CREDIT)){
            affectedAccount.setBalance(affectedAccount.getBalance().add(transaction.getAmount()));
        } else {
            affectedAccount.setBalance(affectedAccount.getBalance().subtract(transaction.getAmount()));
        }
        Mono<Account> resultAccount = accountService.save(affectedAccount);
        return resultAccount
                .flatMap(ac -> {
                    if(ac.getBalance().compareTo(
                            transaction.getAccount().getBalance().add(transaction.getAmount())) == 0){
                        transaction.setAccount(ac);
                        return Mono.just(transaction);
                    } else {
                        logger.error("[ERROR] Sensibilize balance account : transaction type invalid");
                        return Mono.error(
                                new CoreException(
                                        new ApiErrorResponse(EValidationResponse.TRANSACTION_TYPE_INVALID)));
                    }
                });
    }

    private Mono<Transaction> verifyEnoughtMoneyForDebitTransaction(Transaction transaction){
        if(transaction.getAccount().getBalance().compareTo(transaction.getAmount()) >= 0){
            return Mono.just(transaction);
        }
        return Mono.error(
                new CoreException(
                        new ApiErrorResponse(EValidationResponse.TRANSACTION_NOT_EFETIVATED_INSUFFICIENT_FUNDS)));
    }

}
