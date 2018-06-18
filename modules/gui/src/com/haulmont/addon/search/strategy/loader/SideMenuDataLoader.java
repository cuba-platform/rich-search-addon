package com.haulmont.addon.search.strategy.loader;

import com.haulmont.addon.search.context.SearchContext;
import com.haulmont.addon.search.strategy.DefaultSearchEntry;
import com.haulmont.addon.search.strategy.SearchEntry;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides searching functions to {@link com.haulmont.addon.search.strategy.SideMenuSearchStrategy}
 */
public class SideMenuDataLoader {

    protected SearchContext session;
    protected List<DefaultSearchEntry> cached;

    public SideMenuDataLoader(SearchContext session, SideMenu sideMenu) {
        this.session = session;
        cached = mapChildren(sideMenu.getMenuItems()).collect(Collectors.toList());
    }

    protected Stream<DefaultSearchEntry> mapChildren(List<SideMenu.MenuItem> roots) {
        return roots.stream()
                .flatMap(root ->
                        root.getChildren().stream()
                                .map(item -> new FlatMenuItem(item, root))
                                .flatMap(this::traverse)
                                .filter(item -> item.getCommand() != null)
                                .map(item ->
                                        new DefaultSearchEntry(item.getId(), getQueryString(item), getCaption(root, item),
                                                "searchStrategy.sideMenu", item::isVisible)
                                ));
    }

    protected Stream<FlatMenuItem> traverse(FlatMenuItem root) {
        return Stream.concat(Stream.of(root), Optional.ofNullable(root.getChildren())
                .orElse(Collections.emptyList())
                .stream()
                .map(item -> new FlatMenuItem(item, root))
                .flatMap(this::traverse));
    }

    protected String getCaption(SideMenu.MenuItem topRoot, FlatMenuItem item) {
        return topRoot.equals(item.getParent()) ?
                String.format("%s > %s", topRoot.getCaption(), item.getCaption()) :
                String.format("%s > ... > %s", topRoot.getCaption(), item.getCaption());
    }

    protected String getQueryString(FlatMenuItem item) {
        return String.format("%s %s", item.getCaption(),
                ObjectUtils.defaultIfNull(item.getDescription(), ""));
    }

    public List<SearchEntry> load(String pattern) {
        return StringUtils.isBlank(pattern) ?
                Collections.emptyList()
                : cached.stream()
                .filter(e -> e.getQueryString().contains(pattern.trim().toLowerCase()))
                .filter(DefaultSearchEntry::isActive)
                .collect(Collectors.toList());
    }

    protected class FlatMenuItem implements SideMenu.MenuItem {

        protected SideMenu.MenuItem delegate;
        protected SideMenu.MenuItem parent;

        public FlatMenuItem(SideMenu.MenuItem delegate) {
            this.delegate = delegate;
        }

        public FlatMenuItem(SideMenu.MenuItem delegate, SideMenu.MenuItem parent) {
            this.delegate = delegate;
            this.parent = parent;
        }

        @Override
        public String getId() {
            return delegate.getId();
        }

        @Override
        public SideMenu getMenu() {
            return delegate.getMenu();
        }

        @Override
        public String getCaption() {
            return delegate.getCaption();
        }

        @Override
        public void setCaption(String caption) {
        }

        @Override
        public String getDescription() {
            return delegate.getDescription();
        }

        @Override
        public void setDescription(String description) {
        }

        @Override
        public String getIcon() {
            return delegate.getIcon();
        }

        @Override
        public void setIcon(String icon) {
        }

        @Override
        public boolean isCaptionAsHtml() {
            return false;
        }

        @Override
        public void setCaptionAsHtml(boolean captionAsHtml) {

        }

        @Override
        public boolean isVisible() {
            return delegate.isVisible();
        }

        @Override
        public void setVisible(boolean visible) {
        }

        @Override
        public boolean isExpanded() {
            return false;
        }

        @Override
        public void setExpanded(boolean expanded) {

        }

        @Override
        public String getStyleName() {
            return delegate.getStyleName();
        }

        @Override
        public void setStyleName(String styleName) {
        }

        @Override
        public void addStyleName(String styleName) {

        }

        @Override
        public void removeStyleName(String styleName) {

        }

        @Override
        public String getBadgeText() {
            return null;
        }

        @Override
        public void setBadgeText(String badgeText) {

        }

        @Override
        public Consumer<SideMenu.MenuItem> getCommand() {
            return delegate.getCommand();
        }

        @Override
        public void setCommand(Consumer<SideMenu.MenuItem> command) {
        }

        @Override
        public void addChildItem(SideMenu.MenuItem menuItem) {
        }

        @Override
        public void addChildItem(SideMenu.MenuItem menuItem, int index) {
        }

        @Override
        public void removeChildItem(SideMenu.MenuItem menuItem) {
        }

        @Override
        public void removeChildItem(int index) {
        }

        @Override
        public void removeAllChildItems() {

        }

        @Override
        public List<SideMenu.MenuItem> getChildren() {
            return delegate.getChildren();
        }

        @Override
        public boolean hasChildren() {
            return delegate.hasChildren();
        }

        public SideMenu.MenuItem getParent() {
            return parent;
        }

        @Override
        public SideMenu.MenuItem getParentNN() {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            FlatMenuItem that = (FlatMenuItem) o;

            return delegate != null ? delegate.equals(that.delegate) : that.delegate == null;
        }

        @Override
        public int hashCode() {
            return delegate != null ? delegate.hashCode() : 0;
        }
    }
}
