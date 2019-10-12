package ru.dazarnov.wallet.rest.controller;

import spark.RouteGroup;

public interface Controller {

    String getRootPath();

    RouteGroup getRouteGroup();

}
