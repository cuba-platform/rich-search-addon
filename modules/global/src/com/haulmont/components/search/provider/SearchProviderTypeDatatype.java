package com.haulmont.components.search.provider;

import com.haulmont.chile.core.annotations.JavaClass;
import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.cuba.core.global.AppBeans;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.Locale;

@JavaClass(SearchProviderType.class)
public class SearchProviderTypeDatatype implements Datatype<SearchProviderType> {

    static class SearchProviderTypeFactoryHolder {
        static final SearchProviderTypeFactory INSTANCE = AppBeans.get(SearchProviderTypeFactory.NAME);
    }


    @Override
    public String format(@Nullable Object value) {
        if (value == null) {
            return "";
        }
        return ((SearchProviderType)value).name();
    }

    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    @Override
    public SearchProviderType parse(@Nullable String value) throws ParseException {
        return SearchProviderTypeFactoryHolder.INSTANCE.typeBy(value);
    }

    @Nullable
    @Override
    public SearchProviderType parse(@Nullable String value, Locale locale) throws ParseException {
        return SearchProviderTypeFactoryHolder.INSTANCE.typeBy(value);
    }
}
