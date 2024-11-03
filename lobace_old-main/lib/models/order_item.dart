class OrderItem {
  int id;
  int foodId;
  int quantity;
  int userId;
  String idForOrder;

  String foodName;
  String dateCreated;
  String completionStatus;


  OrderItem({
    required this.id,
    required this.foodId,
    required this.quantity,
    required this.userId,
    required this.idForOrder,

    required this.foodName,
    required this.dateCreated,
    required this.completionStatus,
  });

  factory OrderItem.fromJson(Map<String, dynamic> json) {
    return OrderItem(
      id: json['id'],
      foodId: json['foodId'],
      quantity: json['quantity'],
      userId: json['userId'],
      idForOrder: json['idForOrder'],

      foodName: json['foodName'],
      dateCreated: json['dateCreated'],
      completionStatus: json['completionStatus'],
    );
  }
}