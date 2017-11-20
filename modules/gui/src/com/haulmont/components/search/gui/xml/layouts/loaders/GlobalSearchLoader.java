package com.haulmont.components.search.gui.xml.layouts.loaders;

import com.haulmont.components.search.gui.components.GlobalSearch;
import com.haulmont.components.search.presenter.SearchPresenter;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.xml.layout.loaders.AbstractComponentLoader;

public class GlobalSearchLoader extends AbstractComponentLoader<GlobalSearch> {
    @Override
    public void createComponent() {
        resultComponent = (GlobalSearch) factory.createComponent(GlobalSearch.NAME);
        loadId(resultComponent, element);
        AppBeans.get(SearchPresenter.class).decorate(resultComponent, null);
    }

    @Override
    public void loadComponent() {
        assignFrame(resultComponent);
        messagesPack = getContext().getFrame().getMessagesPack();
        loadStyleName(resultComponent, element);
        loadAlign(resultComponent, element);

        loadCaption(resultComponent, element);

        loadWidth(resultComponent, element);
        loadHeight(resultComponent, element);

        loadEnable(resultComponent, element);
        loadVisible(resultComponent, element);

        loadInputPrompt(resultComponent, element);
    }
}
