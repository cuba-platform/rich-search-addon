package com.haulmont.components.search.strategy;

public class HeaderEntry implements SearchEntry {
    protected String id;
    protected String caption;
    protected String type;

    public HeaderEntry() {
    }

    public HeaderEntry(String strategyName) {
        this(strategyName, strategyName, strategyName);
    }

    public HeaderEntry(String id, String caption, String type) {
        this.id = id;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        HeaderEntry that = (HeaderEntry) o;

        if (id != null ? ! id.equals(that.id) : that.id != null) { return false; }
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
