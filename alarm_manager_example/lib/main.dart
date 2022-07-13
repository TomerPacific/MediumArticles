import 'package:flutter/material.dart';
import 'package:android_alarm_manager_plus/android_alarm_manager_plus.dart';
import 'package:flutter/services.dart';
import 'constants.dart';

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
      title: appName,
      theme: ThemeData(
        primarySwatch: Colors.red,
      ),
      home: const MyHomePage(title: appName),
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
      DateTime chosenDate = await _chooseDate();
      await AndroidAlarmManager.oneShotAt(chosenDate, _oneShotAtTaskId, _oneShotAtTaskCallback);
    } else {
      Duration duration = await _chooseDuration();
      await AndroidAlarmManager.oneShot(duration, _oneShotTaskId, _oneShotTaskCallback);
    }
  }

  void _schedulePeriodicAlarm() async {
    Duration duration = await _chooseDuration();
    await AndroidAlarmManager.periodic(duration, _periodicTaskId, _periodicTaskCallback);
  }

  Future<Duration> _chooseDuration() async {
    String duration = "";
    String durationString = durationSeconds;
    AlertDialog alert = AlertDialog(
      title: const Text("Enter a number for the duration"),
      content:
          StatefulBuilder(
            builder: (BuildContext context, StateSetter setState) {
              return  Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                      Expanded(
                        child: RadioListTile(
                            title: const Text(durationSeconds),
                            value: durationSeconds,
                            groupValue: durationString,
                            onChanged: (String? value) {
                              if (value != null) {
                                setState(() =>
                                durationString = value
                                );
                              }
                            }),
                      ),
                      Expanded(
                        child: RadioListTile(
                            title: const Text(durationMinutes),
                            value: durationMinutes,
                            groupValue: durationString,
                            onChanged: (String? value) {
                              if (value != null) {
                                setState(() =>
                                durationString = value
                                );
                              }
                            }),
                      ),
                      Expanded(
                        child: RadioListTile(
                            title: const Text(durationHours),
                            value: durationHours,
                            groupValue: durationString,
                            onChanged: (String? value) {
                              if (value != null) {
                                setState(() =>
                                durationString = value
                                );
                              }
                            }),
                      ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Expanded(child:
                      TextField(
                        maxLines: 1,
                        keyboardType: TextInputType.number,
                        inputFormatters: [FilteringTextInputFormatter.digitsOnly],
                        onChanged: (String text) {
                          duration = text;
                        },
                      ),
                      ),
                    ],
                  ),
                ],
              );
            },
          ),
      actions: [
        TextButton(
        onPressed: () {
          Navigator.of(context).pop(duration);
        },
          child: const Text("Ok"),
        ),
        TextButton(
          onPressed: () {
            Navigator.of(context).pop();
          },
          child: const Text("Cancel"),
        )
      ],
    );
    
    String? enteredText = await showDialog(
        context: context,
        builder: (context) {
      return alert;
    });

    if (enteredText != null) {
        int time = int.parse(enteredText);
        if (durationString == durationSeconds) {
          return Duration(seconds: time);
        } else if (durationString == durationMinutes) {
          return Duration(minutes: time);
        } else {
          return Duration(hours: time);
        }
    }
    return const Duration(seconds: 0);
  }

  Future<DateTime> _chooseDate() async {
    DateTime? chosenDate = await showDatePicker(
        context: context,
        initialDate: DateTime.now(),
        firstDate: DateTime(2022, 7),
        lastDate: DateTime(2101));

    if (chosenDate != null) {
      return chosenDate;
    }

    return DateTime.now();
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
              const Text(
              appName,
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20.0),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Container(
                  margin: const EdgeInsets.only(left: 15),
                  child:  SizedBox(
                    width: 90,
                    height: 50,
                    child: ElevatedButton.icon(
                        onPressed: () {
                          _scheduleOneShotAlarm(false);
                        },
                        icon: const Icon(Icons.plus_one),
                        label: const Text(
                            oneShotAlarm
                        )
                    ),
                  ),
                ),
                SizedBox(
                  width: 120,
                  height: 50,
                  child: ElevatedButton.icon(
                      onPressed: () {
                        _scheduleOneShotAlarm(true);
                      },
                      icon: const Icon(Icons.calendar_today),
                      label: const Text(
                          oneShotAtAlarm
                      )
                  ),
                ),
                Container(
                  margin: const EdgeInsets.only(right: 15),
                  child: SizedBox(
                    width: 112,
                    height: 50,
                    child:  ElevatedButton.icon(
                        onPressed: _schedulePeriodicAlarm,
                        icon: const Icon(Icons.watch_later_outlined),
                        label: const Text(
                            periodicAlarm
                        )
                    ),
                  ),
                ),
              ],
            )
          ],
        ),
      ),
    );
  }
}
