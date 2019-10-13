package ru.dazarnov.wallet.rest.controller;

import ru.dazarnov.wallet.dto.AccountTO;
import ru.dazarnov.wallet.rest.serialization.DeserializationMapper;
import ru.dazarnov.wallet.rest.serialization.SerializationMapper;
import ru.dazarnov.wallet.service.account.AccountService;
import spark.Request;
import spark.Response;
import spark.RouteGroup;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static ru.dazarnov.wallet.rest.util.UtilMessage.*;

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

    private String create(Request request, Response response) {
        try {
            response.status(HttpServletResponse.SC_CREATED);
            AccountTO accountTO = deserializationMapper.readValue(request.body(), AccountTO.class);
            accountService.create(accountTO);
            return SUCCESS_MESSAGE;
        } catch (IOException e) {
            response.status(HttpServletResponse.SC_BAD_REQUEST);
            return ERROR_MESSAGE;
        }
    }

    private String show(Request request, Response response) {
        try {
            response.status(HttpServletResponse.SC_OK);
            long id = Long.parseLong(request.params(":id"));
            Optional<AccountTO> byId = accountService.findById(id);
            if (byId.isPresent()) {
                return serializationMapper.writeValueAsString(byId.get());
            } else {
                response.status(HttpServletResponse.SC_NOT_FOUND);
                return NOT_FOUND_MESSAGE;
            }
        } catch (Exception e) {
            response.status(HttpServletResponse.SC_BAD_REQUEST);
            return ERROR_MESSAGE;
        }
    }

}
