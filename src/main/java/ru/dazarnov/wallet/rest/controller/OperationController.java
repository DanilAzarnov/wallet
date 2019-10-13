package ru.dazarnov.wallet.rest.controller;

import ru.dazarnov.wallet.dto.OperationTO;
import ru.dazarnov.wallet.exception.UnknownAccountException;
import ru.dazarnov.wallet.rest.serialization.DeserializationMapper;
import ru.dazarnov.wallet.rest.serialization.SerializationMapper;
import ru.dazarnov.wallet.service.operation.OperationService;
import spark.Request;
import spark.Response;
import spark.RouteGroup;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.dazarnov.wallet.rest.util.UtilMessage.ERROR_MESSAGE;

public class OperationController implements Controller {

    private final OperationService operationService;
    private final SerializationMapper serializationMapper;
    private final DeserializationMapper deserializationMapper;

    public OperationController(SerializationMapper serializationMapper,
                               DeserializationMapper deserializationMapper,
                               OperationService operationService) {
        this.serializationMapper = serializationMapper;
        this.deserializationMapper = deserializationMapper;
        this.operationService = operationService;
    }

    @Override
    public String getRootPath() {
        return "/operation";
    }

    @Override
    public RouteGroup getRouteGroup() {
        return () -> {
            Spark.post("/send", this::send);
            Spark.get("/show/:id", this::show);
        };
    }

    String send(Request request, Response response) {
        try {
            OperationTO operationTO = deserializationMapper.readValue(request.body(), OperationTO.class);
            operationService.create(operationTO);
            response.status(HttpServletResponse.SC_CREATED);
            return serializationMapper.writeValueAsString(operationTO);
        } catch (IOException | UnknownAccountException e) {
            response.status(HttpServletResponse.SC_BAD_REQUEST);
            return ERROR_MESSAGE;
        }
    }

    String show(Request request, Response response) {
        try {
            long id = Long.parseLong(request.params(":id"));
            response.status(HttpServletResponse.SC_OK);
            return serializationMapper.writeValueAsString(operationService.findById(id));
        } catch (IOException e) {
            response.status(HttpServletResponse.SC_BAD_REQUEST);
            return ERROR_MESSAGE;
        }
    }

}
