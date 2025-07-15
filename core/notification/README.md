# `:core:notification` Module

The `:core:notification` module is a core component responsible for managing and displaying various
types of notifications within the application. It provides a centralized and abstracted way to
interact with the Android notification system, ensuring consistency and ease of use across different
features.

# Purpose and Importance

This module encapsulates all notification-related logic, separating it from the UI or business logic
of other features. This provides several benefits:

- **Centralized Notification Logic:** All notification creation, management, and display logic
  resides in one place, making it easier to maintain and update.

- **Consistency:** Ensures a consistent look, feel, and behavior for all notifications across the
  application.

- **Abstraction:** Provides a clean API for other modules to trigger notifications without needing
  to know the underlying Android Notification API details.

- **Testability:** Makes it easier to test notification behavior independently.

- **Scalability:** Simplifies the process of adding new types of notifications or modifying existing
  ones as the application grows.

## Module Structure

The `:core:notification` module's structure is as follows:

```
notification/
├── build/
├── src
│   ├── androidTest
│   │   └── ...
│   ├── main
│   │   ├── kotlin/com/example/hnotes/core/notification
│   │   │   ├── di
│   │   │   │   └── NotificationModule.kt  # Provides Notification dependencies for DI
│   │   │   ├── Notifier.kt                # Interface for notification operations
│   │   │   ├── NotifierImpl.kt            # Implementation of the Notifier interface
│   │   │   └── Utill.kt                   # Utility functions for notifications
│   │   ├── res                            # Android resources (e.g., notification icons, styles)
│   │   │   └── ...
│   │   └── AndroidManifest.xml            # Module's Android Manifest
│   └── test
│       └── unitTest
│           └── ...
├── build.gradle.kts
└── ...
```

### Key Components:

- `di/NotificationModule.kt`: This Dagger Hilt module is responsible for providing the necessary
  dependencies related to notifications, such as `Notifier` implementation, ensuring they can be
  easily injected into other parts of the application.

- `NotifierImpl.kt`: This class provides the concrete implementation of the `Notifier` interface. It
  contains the actual logic for building and displaying notifications using `NotificationManager`
  and `NotificationCompat.Builder`, adhering to Android's notification best practices.

## Technologies Used

- **Kotlin:** The primary programming language used for all components.

- **Android Notification API:** The native Android framework for creating and managing
  notifications.

- **Dagger Hilt:** For dependency injection, making Notifier instances easily injectable into other
  components.

## Usage

Other modules that need to display notifications will inject the `Notifier` interface and call its
methods. This decouples the notification logic from the calling module, making the codebase cleaner
and more maintainable.

```
// In AlarmReceiver in :core:alarm module

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notifier: Notifier

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getLongExtra(AlarmScheduler.ALARM_EXTRA_ID, 0L)
        notifier.postReminderNotification(id = id)
    }
}
```