package org.igye.sandbox.examplewebapp;

import java.util.Optional;

public interface App {
    String getPropStr(String name);

    Optional<StatefulWebController> lookupController(String path);
}
