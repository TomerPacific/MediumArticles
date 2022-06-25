import 'package:flutter/material.dart';
import 'package:android_alarm_manager_plus/android_alarm_manager_plus.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await AndroidAlarmManager.initialize();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Alarm Manager Example',
      theme: ThemeData(
        primarySwatch: Colors.red,
      ),
      home: const MyHomePage(title: 'Alarm Manager Example'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  final int _oneShotTaskId = 1;
  final int _oneShotAtTaskId = 2;
  final int _periodicTaskId = 3;

  static void _oneShotTaskCallback() {
    print("One Shot Task Running");
  }

  static void _oneShotAtTaskCallback() {
    print("One Shot At Task Running");
  }

  static void _periodicTaskCallback() {
    print("Periodic Task Running");
  }

  void _scheduleOneShotAlarm(bool isTimed) async {
    if (isTimed) {
      await AndroidAlarmManager.oneShotAt(DateTime.now(), _oneShotAtTaskId, _oneShotAtTaskCallback);
    } else {
      await AndroidAlarmManager.oneShot(const Duration(seconds: 10), _oneShotTaskId, _oneShotTaskCallback);
    }
  }

  void _schedulePeriodicAlarm() async {
    await AndroidAlarmManager.periodic(const Duration(seconds: 10), _periodicTaskId, _periodicTaskCallback);
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
              const Text(
              'Alarm Manager Example',
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                TextButton(
                    onPressed: () {
                      _scheduleOneShotAlarm(false);
                    },
                      child: const Text(
                      "One Shot"
                  )
                ),
                TextButton(onPressed: () {
                  _scheduleOneShotAlarm(true);
                },
                    child: const Text(
                        "Timed One Shot"
                    )
                ),
                TextButton(onPressed: _schedulePeriodicAlarm,
                    child: const Text(
                        "Periodic"
                    )
                )
              ],
            )
          ],
        ),
      ),
    );
  }
}
