package com.haulmont.components.search.gui.xml.layouts.loaders;

import com.haulmont.components.search.gui.components.RichSearch;
import com.haulmont.components.search.gui.xml.layouts.loaders.parser.RichSearchConfigurationMapper;
import com.haulmont.components.search.presenter.SearchPresenter;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.xml.layout.loaders.SuggestionFieldLoader;

public class RichSearchLoader extends SuggestionFieldLoader {

    RichSearchConfigurationMapper configurationMapper = AppBeans.get(RichSearchConfigurationMapper.class);

    @Override
    public void createComponent() {
        resultComponent = (RichSearch) factory.createComponent(RichSearch.NAME);
        loadId(resultComponent, element);
    }

    @Override
    public void loadComponent() {
        super.loadComponent();
        AppBeans.get(SearchPresenter.class).init((RichSearch)resultComponent, configurationMapper.parse(context, element));
    }
}
