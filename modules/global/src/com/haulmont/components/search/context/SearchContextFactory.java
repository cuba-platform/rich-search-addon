package com.haulmont.components.search.context;

public interface SearchContextFactory {
    String NAME = "search_SearchContextFactory";

    SearchContext session();
}
