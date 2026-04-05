# Task 4.1 Code Walkthrough Script

## Start

Open [MainActivity.kt](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/MainActivity.kt).

"For Task 4.1, I had to change the app structure compared to 2.1 because this task specifically wanted Fragments, Navigation Component, and Room. So I kept it simple: one activity, two fragments, and local storage with Room."

## Main activity

Look at [MainActivity.kt:8](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/MainActivity.kt#L8).

"This file is small on purpose. It just sets up view binding, gets the nav controller from the nav host fragment, and connects the bottom navigation to it."

## Room data

Open [EventEntity.kt](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/data/EventEntity.kt), [EventDao.kt](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/data/EventDao.kt), and [EventDatabase.kt](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/data/EventDatabase.kt).

"This is the local database part. `EventEntity` defines the event fields, `EventDao` handles CRUD, and `EventDatabase` builds the Room database. The important line in the DAO is the query that sorts events by date, so the dashboard always shows upcoming events in order."

## Event list screen

Open [EventListFragment.kt:21](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/EventListFragment.kt#L21).

"This fragment shows the list of saved events. I set up a RecyclerView with a simple adapter, and I collect the Room flow so the list updates automatically when events are added, edited, or deleted."

Look at [EventListFragment.kt:57](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/EventListFragment.kt#L57).

"This part was useful because I didn’t need extra manual refresh logic. Room gives the data as a flow, and the UI just updates when the database changes."

## Add/edit screen

Open [AddEditEventFragment.kt:24](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/AddEditEventFragment.kt#L24).

"This is the screen for adding and editing events. I kept both actions in one fragment so the app stayed small."

Look at [AddEditEventFragment.kt:45](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/AddEditEventFragment.kt#L45).

"Here I load the database, get the event id if I’m editing, and set up the spinner, buttons, and date picker."

Look at [AddEditEventFragment.kt:69](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/AddEditEventFragment.kt#L69).

"This section loads the existing event into the form when the user presses edit. That way the screen can be reused for both add and update."

## Date and time picker

Look at [AddEditEventFragment.kt:84](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/AddEditEventFragment.kt#L84).

"This part opens the date picker first, then the time picker. One small detail here is that if a date is already selected, I reuse it so edit mode opens on the saved value instead of resetting to now."

## Validation

Open [EventValidator.kt:3](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/util/EventValidator.kt#L3).

"I moved the validation into a small separate object so it was easier to test. It checks that title is not empty, date and time are selected, and that new events cannot be saved in the past."

Then look at [AddEditEventFragment.kt:126](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/AddEditEventFragment.kt#L126).

"This is where the form uses that validator. If there is an error, I show a Snackbar and stop the save. If it passes, I either insert a new event or update the existing one."

## Delete flow

Look at [EventListFragment.kt:72](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/EventListFragment.kt#L72) and [AddEditEventFragment.kt:163](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/AddEditEventFragment.kt#L163).

"Delete works in two places. From the list screen, I can remove an event directly. From the edit screen, I can also delete the current event. Both give user feedback with a Snackbar."

## Test file

Open [EventValidatorTest.kt](/Users/sennanhettirachchi/Downloads/305/app/src/test/java/com/example/travelcompanion/EventValidatorTest.kt).

"I added a small unit test file for the validation logic. It checks blank title, missing date, past date for new events, and that editing an old event is still allowed."

## End

"So overall, the app is simple but it covers all the required parts: CRUD, Room persistence, fragments with navigation, validation, and user feedback."
