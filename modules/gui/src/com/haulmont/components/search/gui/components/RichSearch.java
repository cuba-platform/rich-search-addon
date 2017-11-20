package com.haulmont.components.search.gui.components;

import com.haulmont.components.search.context.SearchContext;
import com.haulmont.components.search.presenter.SearchPresenter;
import com.haulmont.components.search.strategy.SearchEntry;
import com.haulmont.components.search.strategy.SearchStrategy;
import com.haulmont.cuba.gui.components.SuggestionField;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public interface RichSearch extends SuggestionField {
    String NAME = "richSearch";

    void init(SearchContext context, SearchPresenter presenter);

    void resetValue();

    void addValueChangeListener(BiConsumer<SearchContext, SearchEntry> listener);

    void addStrategy(SearchStrategy searchStrategy);

    <T extends SearchEntry> void addStrategy(String name, Function<String, List<T>> searcher,
                                             Consumer<T> invoker);

    void removeStrategy(String name);
}
