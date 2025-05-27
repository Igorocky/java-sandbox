package org.igye.sandbox.examplewebapp.impl;

import lombok.SneakyThrows;
import org.igye.sandbox.examplewebapp.App;
import org.igye.sandbox.examplewebapp.StatefulWebController;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppImpl implements App {

    private final Context context;
    private final Map<String, StatefulWebController> controllers;

    @SneakyThrows
    public AppImpl() {
        this.context = InitialContext.doLookup("java:comp/env");
        this.controllers = Stream.of(
            new TextFormatController()
        ).collect(Collectors.toMap(TextFormatController::getPath, Function.identity()));
    }

    @SneakyThrows
    @Override
    public String getPropStr(String name) {
        return (String) context.lookup(name);
    }

    @Override
    public Optional<StatefulWebController> lookupController(String path) {
        return Optional.ofNullable(controllers.get(path));
    }

    public static App getInstance() {
        return AppHolder.app;
    }

    private static final class AppHolder {
        private static final App app = new AppImpl();
    }
}
