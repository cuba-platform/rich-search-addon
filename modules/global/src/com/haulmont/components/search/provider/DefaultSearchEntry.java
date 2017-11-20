package com.haulmont.components.search.provider;

public class DefaultSearchEntry implements SearchEntry {
    private String id;
    private String queryString;
    private String caption;
    private Integer type;

    public DefaultSearchEntry() {

    }

    public DefaultSearchEntry(String id, String queryString, String caption, SearchProviderType type) {
        this.id = id;
        this.queryString = queryString.toLowerCase();
        this.caption = caption;
        this.type = type == null? -1 : type.id();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public Integer getTypeId() {
        return type;
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

    public void setTypeId(Integer type) {
        this.type = type;
    }

    public void setTypeId(SearchProviderType type) {
        this.type = type.id();
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
