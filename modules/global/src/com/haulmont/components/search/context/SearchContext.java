package com.haulmont.components.search.context;

import com.haulmont.cuba.security.global.UserSession;

import java.io.Serializable;
import java.util.Map;

public interface SearchContext extends Serializable {
    UserSession session();
    Map<String, Object> params();
    void applyUICallback(Runnable callback);
    SearchContext withParams(Map<String, Object> params);
    SearchContext extendParams(Map<String, Object> params);
}
