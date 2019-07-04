package br.com.bank.core.api.handlers;

import br.com.bank.core.entity.Transaction;
import br.com.bank.core.services.implementation.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class TransactionHandler {

    @Autowired
    private TransactionService transactionService;

    public Mono<ServerResponse> executeTransaction(ServerRequest request){
        final Mono<Transaction> transactions = request.bodyToMono(Transaction.class);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(transactions.flatMap(transactionService::executeTransaction), Transaction.class));
    }

}
