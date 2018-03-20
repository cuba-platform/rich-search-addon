package com.haulmont.addon.search.web.configuration;

import com.haulmont.cuba.gui.components.mainwindow.AppMenu;
import com.haulmont.cuba.web.AppUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * Provides application menu {@link AppMenu} injection
 *
 * @see com.haulmont.addon.search.strategy.MainMenuSearchStrategy
 */
@Configuration
public class MenuProvider {

    @Bean("search_AppMenu")
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
    public AppMenu applicationMenu() {
        return ((AppMenu) AppUI.getCurrent().getTopLevelWindow().getComponentNN("mainMenu"));
    }
}
