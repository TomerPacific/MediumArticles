import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Get User Location',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Get User Location', key: UniqueKey()),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({required Key key, required this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  Position? position;
  final GeolocatorPlatform _geolocatorPlatform = GeolocatorPlatform.instance;

  void requestLocationPermission(BuildContext context) async {
    Position currentPosition = await _geolocatorPlatform.getCurrentPosition();

    setState(() {
      position = currentPosition;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Requesting Location Permission'),
      ),
      body:Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Padding(
                padding: const EdgeInsets.all(16.0),
                child: Center(
                  child: new Text(
                      "Requesting Location Permission...",
                      textAlign: TextAlign.center
                  ),
                )
            ),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 16.0),
              child: ElevatedButton(
                  onPressed: () {
                    requestLocationPermission(context);
                  },
                  child: Text('Get Location Permission'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.green
                  ),
              ),
            ),
            Center(
              child: Text(position.toString()),
            )
          ]
      ),
    );
  }
}
