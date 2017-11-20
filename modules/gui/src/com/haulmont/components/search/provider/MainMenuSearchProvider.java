package com.haulmont.components.search.provider;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.haulmont.components.search.context.SearchSession;
import com.haulmont.components.search.context.SessionFactory;
import com.haulmont.components.search.provider.loader.MenuDataLoader;
import com.haulmont.cuba.gui.components.mainwindow.AppMenu;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.haulmont.components.search.provider.DefaultSearchProvider.MENU;

@Component("search_MainMenuSearchProvider")
@Qualifier("menu")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class MainMenuSearchProvider implements SearchProvider {

    protected Cache<SearchSession, MenuDataLoader> dataLoaderMap = CacheBuilder.newBuilder()
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .removalListener((RemovalListener<SearchSession, MenuDataLoader>) notification -> {
                notification.getValue().destroy();
            })
            .build();
    protected SessionFactory sessionFactory;
    protected SparkSession sparkSession;

    @Inject
    protected AppMenu appMenu;

    @Inject
    public MainMenuSearchProvider(SessionFactory sessionFactory, SparkSession sparkSession) {
        this.sessionFactory = sessionFactory;
        this.sparkSession = sparkSession;
    }

    @Override
    public SearchProviderType type() {
        return MENU;
    }

    @Override
    public DataLoader loader(SearchSession context) {
        try {
            dataLoaderMap.cleanUp();
            return dataLoaderMap.get(context, () ->
                    new MenuDataLoader(context, sparkSession, appMenu));
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Consumer<SearchEntry> choiceHandler(SearchSession session) {
        return (value)-> session.applyUICallback(()-> {
            AppMenu.MenuItem item = appMenu.getMenuItem(value.getId());
            item.getCommand().accept(item);
        });
    }
}
