package org.igye.sandbox.examplewebapp.app;

import org.igye.sandbox.examplewebapp.web.StatefulWebController;

import java.util.Optional;

public interface App {
    String getPropStr(String name);

    Optional<StatefulWebController> lookupController(String path);
}
