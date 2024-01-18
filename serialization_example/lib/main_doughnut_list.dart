import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:serialization_example/models/doughnut.dart';
import 'package:serialization_example/models/doughnut_list.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Serialization Example',
      theme: ThemeData(

        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(key: GlobalKey(), title: 'Flutter Serialization Example'),
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
  late DoughnutList _myDoughnuts;
  late String _encoded;
  String _toPresent = "";

  void serialize() {
    setState(() {
      _encoded = jsonEncode(_myDoughnuts);
      _toPresent = _encoded;
    });
  }

  void deserialize() {
    Map<String, dynamic> decodedDoughnut = jsonDecode(_encoded);
    List<dynamic> jsonList =  decodedDoughnut['doughnuts'];
    jsonList.map((elem) => jsonDecode(elem));

    setState(() {
      _toPresent = decodedDoughnut.toString();
    });
  }
  @override
  void initState() {
    _myDoughnut = new Doughnut("Glazed", "None", "Sprinkles", 2.99);
    List<Doughnut> doughnuts = new List<Doughnut>.generate(3, (index) => new Doughnut("A", "B", "C", index + 0.5));
    _myDoughnuts = new DoughnutList(doughnuts);

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
            child: new Container(
                margin: const EdgeInsets.all(10.0),
                child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      new Flexible(
                          child: new Text(
                          "Doughnuts " + (_toPresent.isEmpty ?
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
                      child: new Text("Serialize Doughnut")
                  ),
                ),
                new Container(
                  margin: const EdgeInsets.all(10.0),
                  child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          padding: const EdgeInsets.all(8.0),
                          textStyle: TextStyle(color:Colors.white),
                          backgroundColor: Colors.green,
                       ),
                      onPressed: deserialize,
                      child: new Text("Deserialize Doughnut")
                  ),
                ),
              ],
            ),
          ),
        ],
      )

    );
  }
}
