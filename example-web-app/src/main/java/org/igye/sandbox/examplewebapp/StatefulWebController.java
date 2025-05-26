package org.igye.sandbox.examplewebapp;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public interface StatefulWebController<S, A, R> {
    String getPath();

    S restoreState(HttpServletRequest req);

    Optional<A> decodeAction(HttpServletRequest req, S state);

    S updateState(S state, A action);

    void saveState(S state);

    Pair<R, String> renderState(S state);
}
