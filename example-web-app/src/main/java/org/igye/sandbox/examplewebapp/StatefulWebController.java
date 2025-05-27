package org.igye.sandbox.examplewebapp;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface StatefulWebController<S, A, R> {
    String getPath();

    S loadState(HttpServletRequest req);

    Optional<A> decodeAction(HttpServletRequest req, S state);

    S updateState(S state, A action);

    void saveState(S state);

    String renderState(S state);
}
