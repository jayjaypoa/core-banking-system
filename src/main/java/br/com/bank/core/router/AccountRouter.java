package br.com.bank.core.router;

import br.com.bank.core.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AccountRouter {

    @Bean
    public RouterFunction<ServerResponse> route(AccountHandler accountHandler){
        return RouterFunctions
                .route(GET("/account").and(accept(MediaType.APPLICATION_JSON)), accountHandler::findAll)
                .andRoute(GET("/account/{id}").and(accept(MediaType.APPLICATION_JSON)), accountHandler::findById)
                .andRoute(POST("/account").and(accept(MediaType.APPLICATION_JSON)), accountHandler::save);
    }

}
