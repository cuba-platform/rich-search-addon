package com.haulmont.addon.search.gui.components;

import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.presenter.SearchPresenter;
import com.haulmont.addon.search.strategy.SearchEntry;
import com.haulmont.addon.search.strategy.SearchStrategy;
import com.haulmont.cuba.gui.components.SuggestionField;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Declares view level component for highly customizable searching.
 * @see SearchPresenter
 * @see SearchContext
 * @see SearchEntry
 * @see SearchStrategy
 */
public interface RichSearch extends SuggestionField {
    String NAME = "richSearch";

    /**
     * <p>Method must be called in component loader {@link com.haulmont.addon.search.gui.xml.layouts.loaders.RichSearchLoader}.<br />
     * It links search presentation level with component.
     * </p>
     * @param context search context object with params
     * @param presenter bean, that contains specific search related configuration and has access to search model level
     * <br />
     * @see SearchPresenter#init(com.haulmont.addon.search.gui.components.RichSearch, com.haulmont.addon.search.context.SearchConfiguration)
     */
    void init(SearchContext context, SearchPresenter presenter);

    /**
     * Method must reset component state.
     */
    void resetValue();

    /**
     * Adds value change listener to component. <br />
     * Listener will be invoked on entry choosing.
     */
    void addValueChangeListener(BiConsumer<SearchContext, SearchEntry> listener);

    /**
     * Provides possibility to add named search strategy via controller.
     * @param searchStrategy implementation
     * <br />
     * @see SearchPresenter#addStrategy(com.haulmont.addon.search.strategy.SearchStrategy)
     */
    void addStrategy(SearchStrategy searchStrategy);

    /**
     * Provides possibility to add named search strategy via controller
     * by setting specific data searching method and invoke method
     * <br />
     * @param name searching strategy name
     * @param searcher function, that must present data entry list by specific query
     * @param invoker function, that must declare specific behavior on entry choosing
     * @param <T> depends on entry implementation
     * <br />
     * @see SearchPresenter#addStrategy(java.lang.String, java.util.function.BiFunction, java.util.function.BiConsumer)
     */
    <T extends SearchEntry> void addStrategy(String name, Function<String, List<T>> searcher,
                                             Consumer<T> invoker);

    /**
     * Removes strategy from component configuration
     * @param name searching strategy name
     * <br />
     * @see SearchPresenter#removeStrategy(java.lang.String)
     */
    void removeStrategy(String name);
}
