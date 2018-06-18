package com.haulmont.addon.search.strategy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.context.SearchContextFactory;
import com.haulmont.addon.search.strategy.loader.SideMenuDataLoader;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <p>Implements searching strategy for application main menu.<br />
 * The data loading and searching functions provided by {@link SideMenuDataLoader}
 * </p>
 * Also see web implementation:
 * <pre>com.haulmont.addon.search.web.configuration.MenuProvider</pre>
 */
@Component("search_SideMenuSearchStrategy")
public class SideMenuSearchStrategy implements SearchStrategy {

    protected Cache<SearchContext, SideMenuDataLoader> dataLoaderMap = CacheBuilder.newBuilder()
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .build();
    protected SearchContextFactory searchContextFactory;
    protected SideMenu appMenu;

    @Inject
    public SideMenuSearchStrategy(SideMenu sideMenu, SearchContextFactory searchContextFactory) {
        this.appMenu = sideMenu;
        this.searchContextFactory = searchContextFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SearchEntry> load(SearchContext context, String query) {
        try {
            dataLoaderMap.cleanUp();
            return dataLoaderMap.get(context, () ->
                    new SideMenuDataLoader(context, appMenu)).load(query);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invoke(SearchContext context, SearchEntry value) {
        SideMenu.MenuItem item = appMenu.getMenuItem(value.getId());
        item.getCommand().accept(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return "searchStrategy.sideMenu";
    }
}
