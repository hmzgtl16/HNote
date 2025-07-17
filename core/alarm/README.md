# `:core:alarm` Module

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%
graph LR
  subgraph :core
    :core:alarm["alarm"]
    :core:model["model"]
    :core:notification["notification"]
  end

  :core:alarm --> :core:model
  :core:alarm --> :core:notification
  classDef android-library fill: #3BD482, stroke: #fff, stroke-width: 2px, color: #fff;
  classDef unknown fill: #676767, stroke: #fff, stroke-width: 2px, color: #fff;
  class :core:alarm android-library
  class :core:model unknown
  class :core:notification unknown

```

##   

___

The `:core:alarm` module is a core component responsible for scheduling and managing reminders
within the application. It provides an abstracted and reliable way to interact with the Android
`AlarmManager`, ensuring that time-sensitive events, such as reminders for notes, are triggered
accurately and efficiently.

## Purpose and Importance

This module centralizes all alarm and reminder scheduling logic, separating it from the business
logic of other features. This offers several key benefits:

- **Reliable Scheduling:** Ensures that alarms and reminders are set up correctly using the Android
  system's `AlarmManager`, even when the application is not actively running.

- **Abstraction:** Provides a clean, high-level API for other modules to schedule and cancel alarms
  without needing to delve into the complexities of `AlarmManager` and `PendingIntent`s.

- **Consistency:** Guarantees a uniform approach to handling time-based events across the
  application.

- **Testability:** Makes it easier to test alarm scheduling and reception logic independently.

- **Resource Management:** Can implement best practices for alarm scheduling (e.g., using inexact
  alarms where appropriate) to optimize battery consumption.

## Module Structure

The `:core:alarm` module's structure is as follows:

```
alarm/
├── ...
├── src
│   ├── main
│   │   ├── kotlin/com/example/hnotes/core/alarm
│   │   │   ├── di
│   │   │   │   └── AlarmModule.kt           # Provides Alarm dependencies for DI
│   │   │   ├── AlarmReceiver.kt             # BroadcastReceiver for handling alarm triggers
│   │   │   ├── AlarmScheduler.kt            # Interface for scheduling and canceling alarms
│   │   │   └── AlarmSchedulerImpl.kt        # Implementation of the AlarmScheduler interface
│   │   └── ...
│   └── ...
├── build.gradle.kts
└── ...
```

## Key Components:

- `di/AlarmModule.kt`: This Dagger Hilt module is responsible for providing the necessary
  dependencies related to alarm management, such as the AlarmScheduler implementation, ensuring it
  can be easily injected into other parts of the application.

- `AlarmReceiver.kt`: This is a `BroadcastReceiver` that listens for the system alarms triggered by
  the `AlarmManager`. When an alarm fires, this receiver is activated to handle the event, typically
  by notifying the application (displaying a notification via the `:core:notification` module).

- `AlarmSchedulerImpl.kt`: This class provides the concrete implementation of the `AlarmScheduler`
  interface. It contains the actual logic for setting and canceling alarms using Android's
  `AlarmManager`, creating PendingIntents, and handling different alarm types (e.g., exact, inexact,
  repeating).

## Usage

Other modules that need to set or manage time-based reminders will inject the `AlarmScheduler`
interface and use its methods.

### Example (Conceptual - in NoteRepository from :core:data module):

```
// In NoteRepository from :core:data module

class NoteRepositoryImpl @Inject constructor(
    ...
    private val alarmScheduler: AlarmScheduler
) : NoteRepository {
    ...
    override suspend fun upsertNote(note: Note) {
        ...
        note.reminder?.let {
            ...
            alarmScheduler.schedule(
                id = it.id,
                scheduleTime = it.time,
                repeatMode = it.repeatMode
            )
        }
        ...
    }
}
```