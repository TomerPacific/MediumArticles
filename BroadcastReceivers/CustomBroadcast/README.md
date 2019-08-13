# Custom Broadcast Receiver

This is a sample project, allowing the user to see how to implement and register a broadcast receiver with an Android application using a custom broadcast receiver.

To use a custom broadcast receiver you must declare it in your manifest file inside your application tags.

```<receiver android:name=".MyBroadcastReceiver"  android:exported="true">```
```            <intent-filter>```
```                <action android:name="com.example.broadcast.MY_NOTIFICATION" />```
```            </intent-filter>```
```        </receiver>```

