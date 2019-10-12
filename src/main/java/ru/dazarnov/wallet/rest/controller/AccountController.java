package ru.dazarnov.wallet.rest.controller;

import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.rest.serialization.DeserializationMapper;
import ru.dazarnov.wallet.rest.serialization.SerializationMapper;
import ru.dazarnov.wallet.service.account.AccountService;
import spark.Request;
import spark.Response;
import spark.RouteGroup;
import spark.Spark;

import java.io.IOException;

public class AccountController implements Controller {

    private final SerializationMapper serializationMapper;
    private final DeserializationMapper deserializationMapper;
    private final AccountService accountService;

    public AccountController(SerializationMapper serializationMapper,
                             DeserializationMapper deserializationMapper,
                             AccountService accountService) {
        this.serializationMapper = serializationMapper;
        this.deserializationMapper = deserializationMapper;
        this.accountService = accountService;
    }

    @Override
    public String getRootPath() {
        return "/account";
    }

    @Override
    public RouteGroup getRouteGroup() {
        return () -> {
            Spark.post("/create", this::create);
            Spark.get("/show/:id", this::show);
        };
    }

    Response create(Request request, Response response) {
        AccountTO accountTO;
        try {
            accountTO = deserializationMapper.readValue(request.body(), AccountTO.class);
        } catch (IOException e) {
            response.status(400);
            return response;
        }
        accountService.create(accountTO);
        response.status(200);
        return response;
    }

    Response show(Request request, Response response) {
        try {
            long id = Long.parseLong(request.params(":id"));
            response.body(serializationMapper.writeValueAsString(accountService.findById(id)));
        } catch (IOException e) {
            response.status(400);
            return response;
        }
        response.status(200);
        return response;
    }
}
