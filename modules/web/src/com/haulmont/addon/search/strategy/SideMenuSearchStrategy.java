/*
 * Copyright (c) 2008-2019 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.addon.search.strategy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.context.SearchContextFactory;
import com.haulmont.addon.search.strategy.loader.SideMenuDataLoader;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.web.AppUI;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
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
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class SideMenuSearchStrategy implements SearchStrategy {

    protected Cache<SearchContext, SideMenuDataLoader> dataLoaderMap = CacheBuilder.newBuilder()
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .build();
    protected SearchContextFactory searchContextFactory;
    protected AppUI appUI;

    @Inject
    public SideMenuSearchStrategy(SearchContextFactory searchContextFactory) {
        this.searchContextFactory = searchContextFactory;
        this.appUI = AppUI.getCurrent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SearchEntry> load(SearchContext context, String query) {
        try {
            dataLoaderMap.cleanUp();
            return dataLoaderMap.get(context, () ->
                    new SideMenuDataLoader(context, getAppMenu())).load(query);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invoke(SearchContext context, SearchEntry value) {
        SideMenu.MenuItem item = getAppMenu().getMenuItem(value.getId());
        item.getCommand().accept(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return "searchStrategy.sideMenu";
    }

    protected SideMenu getAppMenu() {
        return ((SideMenu) appUI.getTopLevelWindow().getComponentNN("sideMenu"));
    }
}
