import 'package:serialization_example/models/doughnut.dart';
import 'package:serialization_example/constants.dart';

class DoughnutList {
  final List<Doughnut>? doughnuts;

  DoughnutList(this.doughnuts);

  DoughnutList.fromJson(Map<String, dynamic> json)
  : doughnuts = json[DOUGHNUTS_KEY] != null ? List<Doughnut>.from(json[DOUGHNUTS_KEY]) : null;

  Map<String, dynamic> toJson()  =>
  {
    DOUGHNUTS_KEY: doughnuts,
  };

}