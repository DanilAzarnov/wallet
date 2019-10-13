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

    String create(Request request, Response response) {
        try {
            response.status(200);
            AccountTO accountTO = deserializationMapper.readValue(request.body(), AccountTO.class);
            accountService.create(accountTO);
            return serializationMapper.writeValueAsString(accountTO);
        } catch (IOException e) {
            response.status(400);
            return "Error";
        }
    }

    String show(Request request, Response response) {
        try {
            response.status(200);
            long id = Long.parseLong(request.params(":id"));
            return serializationMapper.writeValueAsString(accountService.findById(id));
        } catch (IOException e) {
            response.status(400);
            return "Error";
        }
    }

}
