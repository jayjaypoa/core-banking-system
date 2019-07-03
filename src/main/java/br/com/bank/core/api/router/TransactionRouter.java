package br.com.bank.core.api.router;

import br.com.bank.core.api.handler.TransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class TransactionRouter {

    @Bean
    public RouterFunction<ServerResponse> route(TransactionHandler transactionHandler){
        return RouterFunctions
                .route(POST("/transaction").and(accept(MediaType.APPLICATION_JSON)),
                        transactionHandler::executeTransaction);
    }

}
