# Project 2 - *NYTimes*

**NYTimes** is an android app that shows articles and allows users to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: **15** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [X] User can click on "settings" which allows selection of **advanced search options** to filter results
* [X] User can configure advanced search filters such as:
  * [X] Begin Date (using a date picker)
  * [X] News desk values (Arts, Fashion & Style, Sports)
  * [X] Sort order (oldest or newest)
* [X] Subsequent searches have any filters applied to the search results
* [X] User can tap on any image in results to see the full text of article **full-screen**
* [X] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.

The following **optional** features are implemented:

* [X] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [X] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
* [X] User can **share an article link** to their friends or email it to themselves
* [X] Replaced Filter Settings Activity with a lightweight modal overlay
* [X] Improved the user interface and experiment with image assets and/or styling and coloring

The following **bonus** features are implemented:

* [X] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the `StaggeredGridLayoutManager` to display improve the grid of image results
* [X] For different news articles that only have text or only have images, use [Heterogenous Layouts](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) with RecyclerView
* [X] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [ ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [X] Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [X] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [ ] Uses [retrolambda expressions](http://guides.codepath.com/android/Lambda-Expressions) to cleanup event handling blocks.
* [X] Leverages the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [X] Leverages the [Retrofit networking library](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) to access the New York Times API.

The following **additional** features are implemented:

* [X] Used ViewPager layout to show top stories 
* [X] Used Calligraphy library to use Newspaper like Font 
* [X] Used Event Bus to communicate Network State change across the application
* [X] Used SharedPreferences to save the filter data 
* [X] Used Card View to show Search Articles 

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/JGOFq7B.gif' width='300'/>

## App Icon

https://www.iconfinder.com/icons/1055019/article_information_news_newspaper_periodical_icon#size=128

## Notes

- Handle URL redirection in Webview 
- Memory Leak with ShareActionProvider 

## Open-source libraries used

- [Retrofit](http://square.github.io/retrofit/) - HTTP Client for Android 
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android
- [ButterKnife](http://jakewharton.github.io/butterknife/) - Injection library to reduce boilerplate view code 
- [Timber](https://github.com/JakeWharton/timber) - Logging Library 
- [Calligraphy](https://github.com/chrisjenx/Calligraphy) - Library to add Custom Font support  
- [Event Bus](https://github.com/greenrobot/EventBus) - Android optimized event bus that simplifies communication between Activities, Fragments, Threads, Services, etc

## License

    Copyright [2016] [Monu Surana]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
