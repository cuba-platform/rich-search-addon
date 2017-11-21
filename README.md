# Rich Search Component
#### Declarative xml strategy usage:
1. **Bean configuration**

    **xml screen config**:
    ```xml
       <window
           class="com.company.test.web.screens.TestWindow" 
           xmlns:search="http://schemas.haulmont.com/cuba/search.xsd">
           <search:richSearch id="search" inputPrompt="msg://searching">
               <search:strategyBean name="search_SearchStrategy" />
           </search:richSearch>
       </window>
    ```
    
    **spring been**:
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
           <search:richSearch id="search" inputPrompt="msg://searching">
               <search:strategy name="custom strategy" searchMethod="search" invokeMethod="invoke" />
           </search:richSearch>
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
#### Programmatically strategy declaration usage:
    
**Controller**:
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


 