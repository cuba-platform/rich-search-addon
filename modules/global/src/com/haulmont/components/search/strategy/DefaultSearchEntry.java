package com.haulmont.components.search.strategy;

/**
 * Implements SearchEntry and declares additional query field for search customization
 * <br />
 * @see SearchEntry
 */
public class DefaultSearchEntry implements SearchEntry {
    protected String id;
    protected String queryString;
    protected String caption;
    protected String type;

    public DefaultSearchEntry() {

    }

    public DefaultSearchEntry(String id, String caption, String type) {
        this.id = id;
        this.queryString = caption.toLowerCase();
        this.caption = caption;
        this.type = type;
    }

    public DefaultSearchEntry(String id, String queryString, String caption, String type) {
        this.id = id;
        this.queryString = queryString.toLowerCase();
        this.caption = caption;
        this.type = type;
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
