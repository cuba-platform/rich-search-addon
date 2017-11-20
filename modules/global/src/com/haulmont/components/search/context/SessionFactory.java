package com.haulmont.components.search.context;

public interface SessionFactory {
    String NAME = "search_SessionFactory";

    SearchSession session();
}
