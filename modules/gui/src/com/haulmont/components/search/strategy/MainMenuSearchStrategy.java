package com.haulmont.components.search.strategy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.haulmont.components.search.context.SearchContext;
import com.haulmont.components.search.context.SearchContextFactory;
import com.haulmont.components.search.strategy.loader.MenuDataLoader;
import com.haulmont.cuba.gui.components.mainwindow.AppMenu;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Implements searching strategy by application main menu.
 * Holds menu session cache in {@code dataLoaderMap}.
 * Data loading and searching provides by {@link MenuDataLoader}
 * <br />
 * @see SearchStrategy
 * <br />
 * Also see web implementation:
 * <pre>com.haulmont.components.search.web.configuration.MenuProvider</pre>
 */
@Component("search_MainMenuSearchStrategy")
public class MainMenuSearchStrategy implements SearchStrategy {

    protected Cache<SearchContext, MenuDataLoader> dataLoaderMap = CacheBuilder.newBuilder()
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .build();
    protected SearchContextFactory searchContextFactory;
    protected AppMenu appMenu;

    @Inject
    public MainMenuSearchStrategy(AppMenu appMenu, SearchContextFactory searchContextFactory) {
        this.appMenu = appMenu;
        this.searchContextFactory = searchContextFactory;
    }

    @Override
    public List<SearchEntry> load(SearchContext context, String query) {
        try {
            dataLoaderMap.cleanUp();
            return dataLoaderMap.get(context, () ->
                    new MenuDataLoader(context, appMenu)).load(query);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void invoke(SearchContext context, SearchEntry value) {
        AppMenu.MenuItem item = appMenu.getMenuItem(value.getId());
        item.getCommand().accept(item);
    }

    @Override
    public String name() {
        return "menu";
    }
}
