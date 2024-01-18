import 'package:serialization_example/models/doughnut.dart';

class DoughnutList {
  final List<Doughnut>? doughnuts;

  DoughnutList(this.doughnuts);

  DoughnutList.fromJson(Map<String, dynamic> json)
  : doughnuts = json['doughnuts'] != null ? List<Doughnut>.from(json['doughnuts']) : null;

  Map<String, dynamic> toJson()  =>
  {
    'doughnuts': doughnuts,
  };

}