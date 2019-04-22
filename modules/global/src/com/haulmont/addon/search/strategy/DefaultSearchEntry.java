/*
 * Copyright (c) 2008-2019 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
