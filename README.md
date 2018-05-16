[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
# Rich Search Component

<img src="https://github.com/cuba-platform/rich-search-addon/raw/master/resources/search.gif" alt="Component image example"/>

## Overview

Global Search [CUBA.Platform](https://www.cuba-platform.com/) application component that will provide user ability to search many types of objects in one search field. The component has already implemented the ability to search by menu items of the application. Search by any entities, their attributes, including related entities can be added programmatically.

## Component Installation
Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 6.8.x            | 1.0.2          |

Add custom application component to your project using CUBA Studio:

* Artifact group: `com.haulmont.addon.search`
* Artifact name: `search-global`
* Version: *add-on version*

## Quick Start

1. Override the main screen via the Studio interface.
1. Add the following code to `ext-mainwindow.xml` as it is given below:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<window xmlns="http://schemas.haulmont.com/cuba/window.xsd"
        class="com.company.rs.web.screens.ExtAppMainWindow"
        extends="/com/haulmont/cuba/web/app/mainwindow/mainwindow.xml"
        messagesPack="com.company.rs.web.screens"
        xmlns:ext="http://schemas.haulmont.com/cuba/window-ext.xsd"
        xmlns:search="http://schemas.haulmont.com/cuba/search.xsd">
    <dialogMode height="600"
                width="800"/>
    <layout>
        <hbox id="titleBar">
            <search:richSearch id="search"
                               align="MIDDLE_LEFT"
                               ext:index="3"
                               inputPrompt="msg://search"
                               suggestionsLimit="200">
                <search:strategyBean name="search_MainMenuSearchStrategy"/>
            </search:richSearch>
        </hbox>
    </layout>
</window>
```

## Data Model

**Search Field**

Has one or more search strategies that are called for each search query.

**Search Strategy**

Specifies which objects should be returned for this search.

**SearchEntry**

Search result objects interface. Used as return types of result of the search strategy. Has id, caption и the name of search strategy which the entry belongs.
   
**DefaultSearchEntry**

Default implementation of SearchEntry.
    
**SearchContext**

Contains context depended data for search mechanism (user session/additional params).
    
**HeaderEntry**

Implements header for grouping of strategy results.


## Usage

Consider using the component in the following example. Make a search by system users. The corresponding editing screen will be opened for the found user.
 
The search will be carried out by login. To do this, use the following code:

```java
LoadContext<User> lc = LoadContext.create(User.class);
lc.setQueryString("select u from sec$User u where u.loginLowerCase like concat('%',:loginLowerCase,'%')")
        .setParameter("loginLowerCase", query.toLowerCase());
```

Let's define search strategies in several ways.

#### Using A Spring Bean As Search Strategy:

**Spring bean**:
```java
@Component("search_UsersSearchStrategy")
public class UsersSearchStrategy implements SearchStrategy {
    @Override
    public List<SearchEntry> load(SearchContext context, String query) {
        LoadContext<User> lc = LoadContext.create(User.class);
        lc.setQueryString("select u from sec$User u where u.loginLowerCase like concat('%',:loginLowerCase,'%')")
                .setParameter("loginLowerCase", query.toLowerCase());

        return dataManager.loadList(lc).stream()
                .map(user -> new DefaultSearchEntry(user.getId().toString(), user.getCaption(), name()))
                .collect(Collectors.toList());
    }

    @Override
    public void invoke(SearchContext context, SearchEntry value) {
        LoadContext<User> lc = LoadContext.create(User.class)
                .setId(UuidProvider.fromString(value.getId()));
        User user = dataManager.load(lc);
        AppUI.getCurrent().getTopLevelWindow().openEditor(user, WindowManager.OpenType.NEW_TAB);
    }
 
    
    @Override
    public String name() {
        return "usersSearchStrategy";
    }
}
```

**XML screen config**:
```xml
<window
    class="com.company.test.web.screens.TestWindow" 
    xmlns:search="http://schemas.haulmont.com/cuba/search.xsd">
    ...
    <layout>
        ...
        <search:richSearch id="search" inputPrompt="msg://search">
            <search:strategyBean name="search_UsersSearchStrategy" />
        </search:richSearch>
        ...
    </layout>
    ...
</window>
```

**Localization**

Add entry to main message pack in format
```text
searchStrategy.{strategyName} = Strategy name
```
For example, 
```text
searchStrategy.usersSearchStrategy = Users
```
    
### Using Controller Methods**

**XML screen config**:
```xml
<window
    class="com.company.test.web.screens.MyWindowController" 
    xmlns:search="http://schemas.haulmont.com/cuba/search.xsd">
    ...
    <layout>
        ...
        <search:richSearch id="search" inputPrompt="msg://search">
            <search:strategy name="usersSearchStrategy" searchMethod="search" invokeMethod="invoke" />
        </search:richSearch>
        ...
    </layout>
    ...
</window>
```
    
**Screen controller**:
```java
public class MyWindowController extends AbstractWindow {
    
    @Inject
    protected DataManager dataManager;

    public List<SearchEntry> search(SearchContext context, String query) {
        LoadContext<User> lc = LoadContext.create(User.class);
        lc.setQueryString("select u from sec$User u where u.loginLowerCase like concat('%',:loginLowerCase,'%')")
                .setParameter("loginLowerCase", query.toLowerCase());

        return dataManager.loadList(lc).stream()
                .map(user -> new DefaultSearchEntry(user.getId().toString(), user.getCaption(), "usersSearchStrategy"))
                .collect(Collectors.toList());
    }
   
    public void invoke(SearchContext context, SearchEntry searchEntry) {
        LoadContext<User> lc = LoadContext.create(User.class)
                .setId(UuidProvider.fromString(searchEntry.getId()));
        User user = dataManager.load(lc);
        AppUI.getCurrent().getTopLevelWindow().openEditor(user, WindowManager.OpenType.NEW_TAB);
    }
}
```

#### Using Programmatic Strategy
    
**Screen controller**:

```java
public class MyWindowController extends AbstractWindow {

    @Inject
    protected DataManager dataManager;

    @Inject
    protected RichSearch search;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        search.addStrategy("usersSearchStrategy", query -> {
            LoadContext<User> lc = LoadContext.create(User.class);
            lc.setQueryString("select u from sec$User u where u.loginLowerCase like concat('%',:loginLowerCase,'%')")
                    .setParameter("loginLowerCase", query.toLowerCase());

            return dataManager.loadList(lc).stream()
                    .map(user -> new DefaultSearchEntry(user.getId().toString(), user.getCaption(), "usersSearchStrategy"))
                    .collect(Collectors.toList());
        }, searchEntry -> {
            LoadContext<User> lc = LoadContext.create(User.class)
                    .setId(UuidProvider.fromString(searchEntry.getId()));
            User user = dataManager.load(lc);
            AppUI.getCurrent().getTopLevelWindow().openEditor(user, WindowManager.OpenType.NEW_TAB);
        });
    }
}
```

The result is:

<img src="https://github.com/cuba-platform/rich-search-addon/raw/master/resources/users-search.gif" alt="User search example"/>


## Known issues

1. The available amount of input should be accounted for each strategy separately (no task, to be discussed).
1. It is required to add an XML declaration for specific strategy parameters (no task, to be discussed).
1. It is required to add keyboard shortcuts for the component and its settings (no task, to be discussed).
