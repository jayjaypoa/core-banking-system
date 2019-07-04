package br.com.bank.core.services.implementation;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.entity.Account;
import br.com.bank.core.entity.Transaction;
import br.com.bank.core.enums.ETransactionType;
import br.com.bank.core.repository.AccountRepository;
import br.com.bank.core.repository.TransactionRepository;
import br.com.bank.core.services.ITransactionService;
import br.com.bank.core.validations.TransactionValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
        if (transaction.getTransactionType().compareTo(ETransactionType.CREDIT) == 0){
            return this.executeCredit(transaction);
        } else if (transaction.getTransactionType().compareTo(ETransactionType.DEBIT) == 0){
            return this.executeDebit(transaction);
        } else {
            // TODO Verificar tipo de retorno em caso de erro
            // throw new Exception("Transaction not recognized");
            return null;
        }
    }

    private Mono<Transaction> executeCredit(Transaction transaction){

        logger.debug("Executing credit transaction");

        // kafkaTemplate.send(TOPIC, transaction);

        transactionValidation.validate(transaction);

        accountService.verifyAccountExistence(transaction.getAccount());

        return transactionRepository.save(transaction);

        // TODO Afetar o saldo na Account. Se OK, enviar confirmação ao Kafka
        // accountRepository.findByBranchAndAccountNumber(transaction.getAccount())
                // .map(objAccount -> objAccount.setBalance(new BigDecimal(15)))
                // .flatMap(accountRepository::save)
                // .subscribe();

    }

    private Mono<Transaction> executeDebit(Transaction transaction){

        logger.debug("Executing debit transaction");

        // transactionRepository.insert(transaction);

        // TODO Verificar se possui saldo. Se possuir, efetivar transação e atualizar saldo na Account.
        // accountRepository.findByBranchAndAccountNumber(transaction.getAccount())
            // .map()
            // .suubscribe();

        return null;
    }

}
