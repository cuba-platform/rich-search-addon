package com.haulmont.addon.search.presenter;

import com.haulmont.addon.search.context.SearchConfiguration;
import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.gui.components.RichSearch;
import com.haulmont.addon.search.strategy.SearchEntity;
import com.haulmont.addon.search.strategy.SearchEntry;
import com.haulmont.addon.search.strategy.SearchStrategy;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * <p>Provides presentation level functions and communicate between component {@link RichSearch} and search strategies.<br />
 * The Presenter implementation been must be annotated as {@code @Scope("prototype")}
 * </p>
 * Default implementation: {@link com.haulmont.addon.search.presenter.impl.SearchPresenterImpl}
 */
public interface SearchPresenter {
    String NAME = "search_SearchPresenter";

    /**
     * <p>Method must be called in component loader {@link com.haulmont.addon.search.gui.xml.layouts.loaders.RichSearchLoader}.<br />
     * It links search presenter with component.
     * </p>
     * @param component          {@link RichSearch} instance
     * @param basicConfiguration parsed from xml {@link com.haulmont.addon.search.gui.xml.layouts.loaders.mapper.RichSearchConfigurationMapper}
     */
    void init(RichSearch component, SearchConfiguration basicConfiguration);

    /**
     * Provides founded data and will be triggered on each search action
     * <br />
     * @param context search context object with params
     * @param query   search pattern
     */
    List<SearchEntity> load(SearchContext context, String query);

    /**
     * Method will be invoked on each choose triggered by search component
     * <br />
     * @param context search context object with params
     * @param entry   chosen value
     */
    void invoke(SearchContext context, SearchEntry entry);

    /**
     * Provides possibility to add named search strategy
     * <br />
     * @param searchStrategy strategy implementation instance
     */
    void addStrategy(SearchStrategy searchStrategy);

    /**
     * Provides possibility to add named search strategy
     * <br />
     * @param name     searching strategy name
     * @param searcher function, that must present data entry list by specific query
     * @param invoker  function, that must declare specific behavior on entry choosing
     * @param <T>      depends on entry {@link SearchEntry} implementation
     */
    <T extends SearchEntry> void addStrategy(String name, BiFunction<SearchContext, String, List<T>> searcher,
                                             BiConsumer<SearchContext, T> invoker);

    /**
     * Removes strategy from component configuration
     * <br />
     * @param name searching strategy name
     */
    void removeStrategy(String name);
}
