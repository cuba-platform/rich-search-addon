# Rich Search Component

![Component image example](resources/search.gif)

## Overview

Global Search CUBA Platform application component that will provide user ability to search many types of objects in one search field.

## Main Screen Installation

1. Add the component to your project using CUBA Studio.
1. Create a HALO theme extension.
1. Override the main screen via the Studio interface.
1. Add the following code to **ext-mainwindow.xml** as it is given below:
    ```xml
       <window xmlns="http://schemas.haulmont.com/cuba/window.xsd"
               class="com.haulmont.searchtest.web.screens.ExtAppMainWindow"
               xmlns:search="http://schemas.haulmont.com/cuba/search.xsd"
               xmlns:main="http://schemas.haulmont.com/cuba/mainwindow.xsd"
               messagesPack="com.haulmont.searchtest.web.screens">
           <dialogMode height="600"
                       width="800"/>
           <layout expand="foldersSplit">
               <hbox id="titleBar" stylename="c-app-menubar"
                     expand="mainMenu" width="100%" height="AUTO"
                     spacing="true" margin="false;false;false;true">
       
                   <embedded id="logoImage" align="MIDDLE_LEFT" type="IMAGE" stylename="c-app-icon"/>
       
                   <main:menu id="mainMenu" align="MIDDLE_LEFT"/>
       
                   <search:richSearch id="search" align="MIDDLE_LEFT" suggestionsLimit="200" inputPrompt="msg://searching">
                      <search:strategyBean name="search_MainMenuSearchStrategy" />
                   </search:richSearch>
       
                   <main:ftsField id="ftsField" align="MIDDLE_LEFT"/>   
 
                   <main:userIndicator id="userIndicator" align="MIDDLE_LEFT"/>
       
                   <main:timeZoneIndicator id="timeZoneIndicator" align="MIDDLE_LEFT"/>
       
                   <hbox id="mainButtonsBox" stylename="c-main-buttons" align="MIDDLE_LEFT">
                       <main:newWindowButton id="newWindowButton"
                                             icon="app/images/new-window.png"
                                             description="msg://com.haulmont.cuba.gui/newWindowBtnDescription"/>
       
                       <main:logoutButton id="logoutButton"
                                          icon="app/images/exit.png"
                                          description="msg://com.haulmont.cuba.gui/logoutBtnDescription"/>
                   </hbox>
               </hbox>
       
               <split id="foldersSplit" width="100%" orientation="horizontal" pos="200px">
                   <main:foldersPane id="foldersPane" width="100%" height="100%"/>
       
                   <main:workArea id="workArea" width="100%" height="100%">
                       <main:initialLayout spacing="true" margin="true">
       
                       </main:initialLayout>
                   </main:workArea>
               </split>
           </layout>
       </window>
    ```

## Usage

#### Using Declarative XML Strategy:

1. **Bean configuration**

    **xml screen config**:
    ```xml
    <window
        class="com.company.test.web.screens.TestWindow" 
        xmlns:search="http://schemas.haulmont.com/cuba/search.xsd">
        ...
        <layout>
            ...
            <search:richSearch id="search" inputPrompt="msg://searching">
                <search:strategyBean name="search_SearchStrategy" />
            </search:richSearch>
            ...
        </layout>
        ...
    </window>
    ```
    **spring bean**:
    ```java
    @Component("search_SearchStrategy")
    public class MySearchStrategy implements SearchStrategy {
        @Override
        public List<SearchEntry> load(SearchContext context, String query) {
            //searching implementation
        }
   
        @Override
        public void invoke(SearchContext context, SearchEntry value) {
            //choosing behavior implementation
        }
     
        
        @Override
        public String name() {
            return "myStrategy";
        }
    }
    ```
    **localization**
    
    Add entry to main message pack in format
    ```text
    searchStrategy.{strategyName} = Strategy name
    ```
    For example, 
    ```text
    searchStrategy.myStrategy = My strategy
    ```
    
1. **Configuration of references for frame methods**

    **xml screen config**:
    ```xml
    <window
        class="com.company.test.web.screens.TestWindow" 
        xmlns:search="http://schemas.haulmont.com/cuba/search.xsd">
        ...
        <layout>
            ...
            <search:richSearch id="search" inputPrompt="msg://searching">
                <search:strategy name="customStrategy" searchMethod="search" invokeMethod="invoke" />
            </search:richSearch>
            ...
        </layout>
        ...
    </window>
    ```
    
    **screen controller**:
    ```java
    public class MyWindowController implements Window {
        public List<SearchEntry> search(SearchContext context, String query) {
            //searching implementation
        }
       
        public void invoke(SearchContext context, SearchEntry searchEntry) {
            //choosing behavior implementation
        }
    }
    ```

#### Using Programmatic Strategy
    
**screen controller**:

```java
    public class MyWindowController implements Window {
    
        @Inject
        private RichSearch search;
    
        @Override
        public void init(Map<String, Object> params) {
            super.init(params);
    
            List<DefaultSearchEntry> variants = new ArrayList<>();
            
            //variants initialisation
            
            search.addStrategy(
                    "custom" /* strategy name */, 
                        (query)-> { /* lambda searching implementation */ },
                        (value)-> { /* lambda choosing behavior */ }
            );
        }
    }
```

## Using Custom Themes

If a custom theme is used in your project, aff the following stypes:

```css
.header-entry-style {
    background-color: silver;
    pointer-events: none;
}

.search-entry-style {
    padding-left:30px;
}
```

## Known issues

1. The available amount of input should be accounted for each strategy separately (no task, to be discussed).
1. It is required to add an XML declaration for specific strategy parameters (no task, to be discussed).
1. It is required to add keyboard shortcuts for the component and its settings (no task, to be discussed).
