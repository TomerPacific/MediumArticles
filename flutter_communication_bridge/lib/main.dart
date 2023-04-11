import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';
import 'package:flutter/services.dart' show rootBundle;

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Communication Bridge',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(title: 'Native - JS Communication Bridge'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, this.title}) : super(key: key);

  final String? title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final GlobalKey<ScaffoldState>? _scaffoldKey = new GlobalKey<ScaffoldState>();
  WebViewController? _controller;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(title: Text('Webview')),
      body: WebView(
        initialUrl: 'about:blank',
        javascriptMode: JavascriptMode.unrestricted,
        javascriptChannels: Set.from([
          JavascriptChannel(
              name: 'messageHandler',
              onMessageReceived: (JavascriptMessage message) {
                ScaffoldMessenger.of(context).showSnackBar(SnackBar(
                  content: Text(message.message),
                ));
              })
        ]),
        onWebViewCreated: (WebViewController webviewController) {
          _controller ??= webviewController;
          _loadHtmlFromAssets();
        },
      ),
      floatingActionButton: FloatingActionButton(
          child: const Icon(Icons.arrow_upward),
          onPressed: () {
            _controller?.evaluateJavascript('fromFlutter("FROM FLUTTER")');
          }),
    );
  }

  _loadHtmlFromAssets() async {
    if (_controller != null) {
      String file = await rootBundle.loadString('assets/index.html');
      _controller?.loadUrl(Uri.dataFromString(file,
              mimeType: 'text/html', encoding: Encoding.getByName('utf-8'))
          .toString());
    }
  }
}
