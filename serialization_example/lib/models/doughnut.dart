import 'package:serialization_example/constants.dart';

class Doughnut {
  final String name;
  final String filling;
  final String topping;
  final double price;

  Doughnut(this.name, this.filling, this.topping, this.price);

  Doughnut.fromJson(Map<String, dynamic> json)
      : name = json[DOUGHNUT_NAME_KEY],
        filling = json[DOUGHNUT_FILLING_KEY],
        topping = json[DOUGHNUT_TOPPING_KEY],
        price = json[DOUGHNUT_PRICE_KEY];

  Map<String, dynamic> toJson() => {
    DOUGHNUT_NAME_KEY : name,
    DOUGHNUT_FILLING_KEY : filling,
    DOUGHNUT_TOPPING_KEY : topping,
    DOUGHNUT_PRICE_KEY : price
  };


}