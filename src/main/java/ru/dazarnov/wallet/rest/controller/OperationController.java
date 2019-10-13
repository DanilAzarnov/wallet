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

import java.io.IOException;

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
            response.status(201);
            return serializationMapper.writeValueAsString(operationTO);
        } catch (IOException | UnknownAccountException e) {
            response.status(400);
            return "Error";
        }
    }

    String show(Request request, Response response) {
        try {
            long id = Long.parseLong(request.params(":id"));
            response.status(200);
            return serializationMapper.writeValueAsString(operationService.findById(id));
        } catch (IOException e) {
            response.status(400);
            return "Error";
        }
    }

}
