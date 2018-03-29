package com.haulmont.addon.search.strategy;

import com.haulmont.bali.util.Preconditions;

/**
 * Implements header for grouping of strategy results.
 */
public class HeaderEntry implements SearchEntry {
    protected String id;
    protected String caption;
    protected String type;

    public HeaderEntry() {
    }

    public HeaderEntry(String strategyName) {
        this(strategyName, strategyName, strategyName);
    }

    public HeaderEntry(String id, String type) {
        this(id, type.toLowerCase(), type);
    }

    public HeaderEntry(String id, String caption, String type) {
        Preconditions.checkNotNullArgument(id);
        Preconditions.checkNotNullArgument(type);

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HeaderEntry that = (HeaderEntry) o;

        if (! id.equals(that.id)) {
            return false;
        }
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
