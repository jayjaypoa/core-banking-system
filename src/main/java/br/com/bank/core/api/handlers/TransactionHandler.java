package br.com.bank.core.api.handlers;

import br.com.bank.core.entity.Transaction;
import br.com.bank.core.services.implementation.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
public class TransactionHandler {

    private static final Logger logger = LoggerFactory.getLogger(TransactionHandler.class);

    @Autowired
    private TransactionService transactionService;

    public Mono<ServerResponse> executeTransaction(final ServerRequest request){
        logger.info("Endpoint called - executeTransaction");
        return request.body(BodyExtractors.toMono(Transaction.class))
                .flatMap(transactionService::executeTransaction)
                .flatMap(requestBody -> {
                    logger.info("Request Body : {}", requestBody);
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromObject(requestBody));
                });

    }

}
