package com.haulmont.addon.search.strategy;

import java.util.function.Supplier;

public class DefaultSearchEntry implements SearchEntry {
    protected String id;
    protected String queryString;
    protected String caption;
    protected String type;
    protected Supplier<Boolean> isActive;

    public DefaultSearchEntry() {}

    public DefaultSearchEntry(String id, String caption, String type) {
        this(id, caption.toLowerCase(), caption, type, null);
    }

    public DefaultSearchEntry(String id, String queryString, String caption, String type) {
        this(id, queryString, caption, type, null);
    }

    public DefaultSearchEntry(String id, String queryString, String caption, String type, Supplier<Boolean> isActive) {
        this.id = id;
        this.queryString = queryString.toLowerCase();
        this.caption = caption;
        this.type = type;
        this.isActive = isActive == null ? ()-> true : isActive;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public String getStrategyName() {
        return type;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(isActive.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultSearchEntry that = (DefaultSearchEntry) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
