# Task 4.1 Problems And Fixes

## 1. The task forced a different Android structure

The biggest change was that this task could not be done the same way as 2.1. The PDF explicitly required Room, Navigation Component, and Fragments. So I had to move away from the old single-screen Compose approach and rebuild it as:

- one activity
- two fragments
- bottom navigation
- Room database

The solution was to keep the structure minimal instead of making it complex.

## 2. Add and edit needed to share one screen

At first, add and edit can easily turn into two separate screens with repeated code. I wanted to keep it small, so I used one fragment for both. The harder part was making it switch correctly between "add" mode and "edit" mode based on the event id.

That logic is mainly in [AddEditEventFragment.kt:45](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/AddEditEventFragment.kt#L45) to [AddEditEventFragment.kt:66](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/AddEditEventFragment.kt#L66).

## 3. Date validation was trickier than it looked

The assignment says users should not be able to create new events in the past. But when editing an event, the event might already be in the past and still need to be changed. So I had to separate the logic for new events and existing events instead of blocking both.

That fix is in [EventValidator.kt:15](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/util/EventValidator.kt#L15).

## 4. Keeping the selected date when editing

Another smaller issue was the date/time picker. If the user edits an event, the picker should open on the current saved event time, not reset to the current date every time. Without that, the edit flow feels rough and the user has to reselect everything.

That fix is in [AddEditEventFragment.kt:84](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/AddEditEventFragment.kt#L84) to [AddEditEventFragment.kt:123](/Users/sennanhettirachchi/Downloads/305/app/src/main/java/com/example/travelcompanion/ui/AddEditEventFragment.kt#L123).

## 5. AGP 9 build changes caused setup problems

The app initially failed to build because AGP 9 no longer wants the old `org.jetbrains.kotlin.android` plugin in the module build. I had to switch the Gradle setup to built-in Kotlin and use the compatible kapt plugin path for Room.

This was not an app logic issue. It was a real environment and build configuration issue.

## 6. Resource style mismatch

Another build error came from using a Material outlined button style name that was not actually available in the current dependency set. That stopped Android resource linking until I changed the button style to a valid one.

## 7. Why the code stayed simple

Even though this task involved more Android pieces than 2.1, I kept it simple on purpose:

- no extra activities
- no heavy architecture
- no unnecessary layers
- only a few short comments where the logic is not obvious

That kept it closer to something a student would actually build while still meeting the assignment requirements.
