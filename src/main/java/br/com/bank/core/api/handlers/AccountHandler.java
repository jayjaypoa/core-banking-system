package br.com.bank.core.api.handlers;

import br.com.bank.core.entity.Account;
import br.com.bank.core.exceptions.AccountException;
import br.com.bank.core.services.implementation.AccountService;
import br.com.bank.core.util.HandlerResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AccountHandler {

    @Autowired
    private AccountService accountService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountService.findAll(), Account.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable("id");
        return accountService.findById(id)
                .flatMap(resp -> ServerResponse.ok().body(BodyInserters.fromObject(resp)))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(AccountException.class,
                        error -> HandlerResponseUtils.badRequest(error.getErrorResponse(), request));
    }

    public Mono<ServerResponse> save(ServerRequest request){
        final Mono<Account> accounts = request.bodyToMono(Account.class);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(accounts.flatMap(accountService::save), Account.class));
    }

    public Mono<ServerResponse> getCurrentBalance(ServerRequest request){
        Account accountFilter =
                new Account(request.pathVariable("branchNumber"), request.pathVariable("accountNumber"));
        return accountService.getCurrentBalance(accountFilter)
                .flatMap(resp -> ServerResponse.ok().body(BodyInserters.fromObject(resp)))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(AccountException.class,
                        error -> HandlerResponseUtils.badRequest(error.getErrorResponse(), request));
    }

}
