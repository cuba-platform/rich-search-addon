package com.haulmont.components.search.strategy;

import com.haulmont.components.search.context.SearchContext;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ContextualSearchStrategy<T extends SearchEntry> implements SearchStrategy<T> {

    String name;
    BiFunction<SearchContext, String, List<T>> searcher;
    BiConsumer<SearchContext, T> invoker;

    public ContextualSearchStrategy(String name, BiFunction<SearchContext, String, List<T>> searcher,
                                    BiConsumer<SearchContext, T> invoker) {
        this.name = name;
        this.searcher = searcher;
        this.invoker = invoker;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<T> load(SearchContext context, String query) {
        return searcher.apply(context, query);
    }

    @Override
    public void invoke(SearchContext context, T value) {
        invoker.accept(context, value);
    }
}
