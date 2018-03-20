package com.haulmont.addon.search.gui.xml.layouts.loaders;

import com.haulmont.addon.search.gui.components.RichSearch;
import com.haulmont.addon.search.gui.xml.layouts.loaders.mapper.RichSearchConfigurationMapper;
import com.haulmont.addon.search.presenter.SearchPresenter;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.xml.layout.loaders.SuggestionFieldLoader;

/**
 * <p>Create and load {@link RichSearch} component to frame.<br />
 * Also links component with presenter {@link SearchPresenter}.</p>
 *
 * Configuration mapper: {@link RichSearchConfigurationMapper}
 */
public class RichSearchLoader extends SuggestionFieldLoader {

    protected RichSearchConfigurationMapper configurationMapper = AppBeans.get(RichSearchConfigurationMapper.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void createComponent() {
        resultComponent = (RichSearch) factory.createComponent(RichSearch.NAME);
        loadId(resultComponent, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadComponent() {
        super.loadComponent();
        AppBeans.get(SearchPresenter.class).init((RichSearch)resultComponent, configurationMapper.map(context, element));
    }
}
