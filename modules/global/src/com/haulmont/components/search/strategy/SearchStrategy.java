package com.haulmont.components.search.strategy;

import com.haulmont.components.search.context.SearchContext;

import java.util.List;

public interface SearchStrategy<T extends SearchEntry>  {

    String name();

    List<T> load(SearchContext context, String query);

    void invoke(SearchContext context, T value);
}
