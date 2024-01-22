import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:serialization_example/models/doughnut.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Serialization Example',
      theme: ThemeData(

        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Serialization Example', key: GlobalKey()),
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

  late Doughnut _myDoughnut;
  late String _encoded;
  String _toPresent = "";

  void serialize() {
    setState(() {
      _encoded = jsonEncode(_myDoughnut);
      _toPresent = _encoded;
    });
  }

  void deserialize() {
    var decodedDoughnut = jsonDecode(_encoded);
    setState(() {
      _toPresent = decodedDoughnut.toString();
    });
  }
  @override
  void initState() {
    _myDoughnut = new Doughnut("Glazed", "None", "Sprinkles", 2.99);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Column(
        children: <Widget>[
          Center(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                new Container(
                  margin: const EdgeInsets.all(10.0),
                  child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        padding: const EdgeInsets.all(8.0),
                        textStyle: TextStyle(color: Colors.white),
                        backgroundColor: Colors.red,
                      ),
                      onPressed: serialize,
                      child: const Text("Serialize Doughnut")
                  ),
                ),
                new Container(
                  margin: const EdgeInsets.all(10.0),
                  child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          padding: const EdgeInsets.all(8.0),
                          textStyle: TextStyle(color: Colors.white),
                          backgroundColor: Colors.green),
                      onPressed: deserialize,
                      child: const Text("Deserialize Doughnut")
                  ),
                ),
              ],
            ),
          ),
          Center(
              child: new Container(
                  margin: const EdgeInsets.all(10.0),
                  child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        new Flexible(
                            child: new Text(
                                "Doughnut Object " + (_toPresent.isEmpty ?
                                "Doughnut name : " + _myDoughnut.name +
                                    " and is filled with " + _myDoughnut.filling +
                                    " and is topped with " + _myDoughnut.topping +
                                    " and costs " + _myDoughnut.price.toString()
                                    : _toPresent)
                            )
                        )
                      ]
                  )
              )
          ),
          Center(
                child: new Container(
                    margin: const EdgeInsets.all(10.0),
                    child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                    new Flexible(
                        child: Image.asset('assets/images/doughnut.png'),
                    )
                  ]
                )
            )
          )
        ],
      )

    );
  }
}
