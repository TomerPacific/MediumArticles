import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Get User Location',
      theme: ThemeData(

        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Get User Location'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  Position? position;
  final GeolocatorPlatform _geolocatorPlatform = GeolocatorPlatform.instance;
  bool isLocationPermissionGranted = false;
  bool isLocationPermissionDeniedForever = false;

  void requestLocationPermission() async {

    if (isLocationPermissionDeniedForever) {
      return;
    }

    if (isLocationPermissionGranted) {
      getLocation();
      return;
    }

    LocationPermission locationPermission = await _geolocatorPlatform.checkPermission();
    if (locationPermission == LocationPermission.denied) {
      locationPermission = await _geolocatorPlatform.requestPermission();
      if (locationPermission == LocationPermission.always) {
        isLocationPermissionGranted = true;
        getLocation();
      }
    } else if (locationPermission == LocationPermission.deniedForever) {
      isLocationPermissionDeniedForever = true;
    }
  }

  void getLocation() async {
    Position currentPosition = await _geolocatorPlatform.getCurrentPosition();

    setState(() {
      position = currentPosition;
    });
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            ElevatedButton(
              onPressed: () {
                requestLocationPermission();
              },
              style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green
              ),
              child: const Text('Get Location Permission'),
            ),
            Center(
              child: Text(position.toString()),
            )
          ],
        ),
      ),
    );
  }
}
