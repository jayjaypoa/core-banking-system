package br.com.bank.core.validations;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.entity.Transaction;
import br.com.bank.core.enums.EValidationResponse;
import br.com.bank.core.exceptions.CoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.Validator;
import java.math.BigDecimal;

@Component
public class TransactionValidation {

    @Autowired
    public TransactionValidation(){}

    public Mono<Transaction> validate(Transaction transaction) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();

        if (!this.validateAccount(transaction)) {
            apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ACCOUNT_PARAM);
            return Mono.error(new CoreException(apiErrorResponse));
        } else if (!this.validateAmountNegativeOrZero(transaction)) {
            apiErrorResponse.setError(EValidationResponse.VALIDATION_TRANSACTION_ERROR_AMOUNT_NEGATIVE_OR_ZERO);
            return Mono.error(new CoreException(apiErrorResponse));
        }

        return Mono.just(transaction);
    }

    private boolean validateAccount(Transaction transaction) {
        if (transaction.getAccount().getBranchNumber().isBlank()
                || transaction.getAccount().getAccountNumber().isBlank()) {
            return false;
        }
        return true;
    }

    private boolean validateAmountNegativeOrZero(Transaction transaction) {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return true;
    }

}
