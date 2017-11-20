package com.haulmont.components.search.context;

import com.haulmont.cuba.security.global.UserSession;

import java.io.Serializable;

public interface SearchSession extends Serializable {
    UserSession userSession();
    void applyUICallback(Runnable callback);
}
