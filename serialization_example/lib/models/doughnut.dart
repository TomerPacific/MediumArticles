class Doughnut {
  final String name;
  final String filling;
  final String topping;
  final double price;

  Doughnut(this.name, this.filling, this.topping, this.price);

  Doughnut.fromJson(Map<String, dynamic> json)
      : name = json['name'],
        filling = json['filling'],
        topping = json['topping'],
        price = json['price'];

  Map<String, dynamic> toJson() => {
    'name' : name,
    'filling' : filling,
    'topping' : topping,
    'price' : price
  };


}