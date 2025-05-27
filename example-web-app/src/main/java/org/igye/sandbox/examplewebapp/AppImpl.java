package org.igye.sandbox.examplewebapp;

import lombok.SneakyThrows;
import org.igye.sandbox.examplewebapp.controllers.IndexController;
import org.igye.sandbox.examplewebapp.controllers.TextFormatController;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Collections;
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
        Map<String, StatefulWebController> allControllers = Stream.of(
            new TextFormatController()
        ).collect(Collectors.toMap(TextFormatController::getPath, Function.identity()));
        allControllers.put(
            "",
            new IndexController(allControllers.values().stream().map(StatefulWebController::getPath).sorted().toList())
        );
        this.controllers = Collections.unmodifiableMap(allControllers);
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
