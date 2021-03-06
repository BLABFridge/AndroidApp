The current plan for the Android app is three buttons and a display screen.

The buttons:
1. Returns sorted list of the fridge.
2. Returns a sorted list of items expiring by specified date.
3. Provides a search bar to allow for searching for particular item.

There will be a second screen for viewing the lists, and reading the notifications.
This window will have a back button to get back to the main screen.


## Opcodes for interfacing with Android App

5 - Notify User : Create a new notification containing the given string, used when food items are going to expire. The app acknowledges with a blank 5 packet
#####Format
	5[0][String notificationString][0][padding to 100 bytes]

6 - FoodItem does not exist : When the scanned item does not exist in the database, the phone will be notified to enter item if desired, generally responded to by a 1 packet
#####Format
	6[0][String missingTagCode][0][padding to 100 bytes]

1 - FoodItem Returned : Generally in response to a 6 packet, and the same format as a 1 packet 
#####Format
	1[0][String FoodItem name][0][String lifetimeInDays][0][String daysFromNow(optional)][0][padding to 100 bytes]

8 - Enter adding mode - sent to the listener from the android app to automatically enter adding mode
#####Format
	8[0][Optional timeout][0][padding to 100 bytes]

Note on services for the notifications --> https://developer.android.com/guide/components/services.html
Note on notifications --> https://developer.android.com/guide/topics/ui/notifiers/notifications.html
