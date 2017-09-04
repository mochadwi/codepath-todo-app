# Pre-work - *Todo-Go*

**Todo-Go** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Mochamad Iqbal Dwi Cahyo**

Time spent: **30** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [ ] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into Realm](https://realm.io/docs/java/latest/) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://www.dropbox.com/s/k1kt40xece87afi/todo-go.mp4?dl=0' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [Quicktime Player](https://support.apple.com/downloads/quicktime).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** Somewhat challenging when first getting started build apps. Also, interesting when tackle a few ones.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** 
* convertView: To convert View object / class to desired view (Header, Item, Footer).
* getView: Get current View object / class to display it.
* ArrayAdapter: The background thread that do the hard work to populate data on the list for ListView / RecyclerView.
This is important, when using custom layout. So, we gain control by customizing how data should looks like

## Notes

Describe any challenges encountered while building the app.

- https://www.evernote.com/l/AZmAY5sYLqhP4rSfM6oH7TChUW0Ps7VbDFk
- Been struggling to solve error for deprecated libs xD
- Only a few common core functionality implemented.
- Layout/design, not so pretty tho

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
# codepath-todo-app
Journey to Android, Android Fundamental, trying new library, etc
