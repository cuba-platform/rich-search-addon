package com.haulmont.components.search.presenter;

import com.haulmont.components.search.context.SearchConfiguration;
import com.haulmont.components.search.context.SearchContext;
import com.haulmont.components.search.gui.components.RichSearch;
import com.haulmont.components.search.strategy.SearchEntity;
import com.haulmont.components.search.strategy.SearchEntry;
import com.haulmont.components.search.strategy.SearchStrategy;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Provides presentation level functions and communicate between component {@link RichSearch} and search strategies.
 * The Presenter implementation been must be annotated as {@code @Scope("prototype")}
 * because contains specific, not sharable logic and data.
 * <br />
 * Default implementation:
 * @see com.haulmont.components.search.presenter.impl.SearchPresenterImpl
 */
public interface SearchPresenter {
    String NAME = "search_SearchPresenter";

    /**
     * Method must be called in component loader {@link com.haulmont.components.search.gui.xml.layouts.loaders.RichSearchLoader}.
     * It links search presentation level with component.
     * <br />
     * @param component instance
     * @param basicConfiguration parsed from xml {@link com.haulmont.components.search.gui.xml.layouts.loaders.parser.RichSearchConfigurationMapper}
     */
    void init(RichSearch component, SearchConfiguration basicConfiguration);

    /**
     * Provides founded data
     * and will be triggered on each search action
     * @param context search object with params
     * @param query pattern
     * @return
     */
    List<SearchEntity> load(SearchContext context, String query);

    /**
     * Method will be invoked on each choose triggered by search component
     * <br />
     * @param context search object with params
     * @param entry chosen
     */
    void invoke(SearchContext context, SearchEntry entry);

    /**
     * Provides possibility to add named search strategy
     * @param searchStrategy implementation
     */
    void addStrategy(SearchStrategy searchStrategy);

    /**
     * Provides possibility to add named search strategy
     * @param name of searching strategy
     * @param searcher function, that must present data entry list by specific query
     * @param invoker function, that must declare specific behavior on entry choosing
     * @param <T> depends on entry {@link SearchEntry} implementation
     */
    <T extends SearchEntry> void addStrategy(String name, BiFunction<SearchContext, String, List<T>> searcher,
                                                    BiConsumer<SearchContext, T> invoker);

    /**
     * Removes strategy from component configuration
     * @param name of strategy
     */
    void removeStrategy(String name);
}
