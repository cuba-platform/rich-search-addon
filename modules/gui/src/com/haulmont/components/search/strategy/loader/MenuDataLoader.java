package com.haulmont.components.search.strategy.loader;

import com.haulmont.components.search.context.SearchContext;
import com.haulmont.components.search.strategy.DefaultSearchEntry;
import com.haulmont.components.search.strategy.SearchEntry;
import com.haulmont.cuba.gui.components.mainwindow.AppMenu;
import org.apache.commons.lang.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides searching functions to {@link com.haulmont.components.search.strategy.MainMenuSearchStrategy}
 */
public class MenuDataLoader {

    protected SearchContext session;
    protected List<DefaultSearchEntry> cached;

    public MenuDataLoader(SearchContext session, AppMenu appMenu) {
        this.session = session;
        cached = getChildren(appMenu.getMenuItems()).map(item-> new DefaultSearchEntry(
                            item.getId(),
                            String.format("%s %s", item.getCaption(),
                            ObjectUtils.defaultIfNull(item.getDescription(), "")),
                item.getCaption(),
                "menu"
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

    public List<SearchEntry> load(String pattern) {
        return cached.stream().filter(e-> e.getQueryString().contains(pattern.toLowerCase())).collect(Collectors.toList());
    }

}
