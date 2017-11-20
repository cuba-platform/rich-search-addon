package com.haulmont.components.search.provider.loader;

import com.haulmont.components.search.context.SearchSession;
import com.haulmont.components.search.provider.DataLoader;
import com.haulmont.components.search.provider.DefaultSearchEntry;
import com.haulmont.components.search.provider.DefaultSearchProvider;
import com.haulmont.components.search.provider.SearchEntry;
import com.haulmont.cuba.gui.components.mainwindow.AppMenu;
import org.apache.commons.lang.ObjectUtils;
import org.apache.spark.sql.SparkSession;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MenuDataLoader implements DataLoader {

    protected SearchSession session;
    protected List<SearchEntry> cached;

    public MenuDataLoader(SearchSession session, SparkSession sparkSession, AppMenu appMenu) {
        this.session = session;
        cached = getChildren(appMenu.getMenuItems()).map(item-> new DefaultSearchEntry(
                            item.getId(),
                            String.format("%s %s", item.getCaption(),
                            ObjectUtils.defaultIfNull(item.getDescription(), "")),
                item.getCaption(),
                DefaultSearchProvider.MENU
        )).collect(Collectors.toList());
    }

    private Stream<AppMenu.MenuItem> getChildren(List<AppMenu.MenuItem> roots) {
        return roots.stream()
                .flatMap(root-> root.getChildren().stream())
                .flatMap(this::traverse);
    }

    private Stream<AppMenu.MenuItem> traverse(AppMenu.MenuItem root) {
        return Stream.concat(Stream.of(root), Optional.ofNullable(root.getChildren())
                .orElse(Collections.emptyList())
                .stream().flatMap(this::traverse));
    }

    @Override
    public List<SearchEntry> query(String pattern) {
        return cached.stream().filter(e-> e.getQueryString().contains(pattern.toLowerCase())).collect(Collectors.toList());
    }

}
