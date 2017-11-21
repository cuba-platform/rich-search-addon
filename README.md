# Rich Search Component

![Component image example](/img/search.gif =500x)

## Main screen installation

1. Add component via Studio in your project
2. Add code into **web-spring.xml**:

    ```xml
       <beans xmlns="http://www.springframework.org/schema/beans"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:context="http://www.springframework.org/schema/context"
              xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">
           ...
           <!-- Annotation-based beans -->
           <context:component-scan base-package="com.haulmont.components"/>
           ...
       </beans>
    ```
3. Override main screen via Studio interface
4. Add code into **ext-mainwindow.xml** as indicated below:
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

#### Declarative xml strategy usage:

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
    }
    ```
2. **Frame method referential configuration**

    **xml screen config**:
    ```xml
    <window
        class="com.company.test.web.screens.TestWindow" 
        xmlns:search="http://schemas.haulmont.com/cuba/search.xsd">
        ...
        <layout>
            ...
            <search:richSearch id="search" inputPrompt="msg://searching">
                <search:strategy name="custom strategy" searchMethod="search" invokeMethod="invoke" />
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

#### Programmatically strategy usage:
    
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