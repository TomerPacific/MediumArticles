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
      DateTime chosenDate = await _chooseDate();
      await AndroidAlarmManager.oneShotAt(chosenDate, _oneShotAtTaskId, _oneShotAtTaskCallback);
    } else {
      int duration = await _chooseDuration();
      await AndroidAlarmManager.oneShot(const Duration(seconds: 10), _oneShotTaskId, _oneShotTaskCallback);
    }
  }

  void _schedulePeriodicAlarm() async {
    await AndroidAlarmManager.periodic(const Duration(seconds: 10), _periodicTaskId, _periodicTaskCallback);
  }

  Future<int> _chooseDuration() async {
    String duration = "";
    String durationString = "seconds";
    AlertDialog alert = AlertDialog(
      title: const Text("Enter a number for duration"),
      content:
          StatefulBuilder(
            builder: (BuildContext context, StateSetter setState) {
              return  Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Expanded(
                        child: RadioListTile(
                            title: const Text("Seconds"),
                            value: "Seconds",
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
                            title: const Text("Minutes"),
                            value: "Minutes",
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
                            title: const Text("Hours"),
                            value: "Hours",
                            groupValue: durationString,
                            onChanged: (String? value) {
                              if (value != null) {
                                setState(() =>
                                durationString = value
                                );
                              }
                            }),
                      ),
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Expanded(child:
                      TextField(
                        maxLines: 1,
                        keyboardType: TextInputType.number,
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
        return int.parse(enteredText);
    }
    return 0;
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
              'Alarm Manager Example',
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
                            "One Shot"
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
                          "Timed One Shot"
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
                            "Periodic"
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
