import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:food/models/order.dart';
import 'package:food/models/order_card.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class Orders extends StatefulWidget {
  const Orders({super.key});

  @override
  State<Orders> createState() => _OrdersState();
}

class Customshape extends CustomClipper<Path> {
  @override
  Path getClip(Size size) {
    double height = size.height;
    double width = size.width;

    var path = Path();
    path.lineTo(0, height - 60);
    path.quadraticBezierTo(width / 2, height, width, height - 60);
    path.lineTo(width, 0);
    path.close();
    return path;
  }

  @override
  bool shouldReclip(covariant CustomClipper<Path> oldClipper) {
    return true;
  }
}

class _OrdersState extends State<Orders> {
  List<Order> orders = [];

  @override
  void initState() {
    super.initState();
    findOrdersByUserId(context);
  }

  Future<void> findOrdersByUserId(BuildContext context) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    int? userId = prefs.getInt('userId');

    if (userId != null) {
      var uri = Uri.parse("http://10.0.2.2:8080/order/find-order-by-user/$userId");
      Map<String, String> header = {"Content-Type": "application/json"};
      var response = await http.post(uri, headers: header);

      if (response.statusCode == 200) {
        var parsedJson = jsonDecode(response.body) as Map<String, dynamic>;
        List<Order> fetchedOrders = Order.fromJson(parsedJson);

        setState(() {
          orders = fetchedOrders;
        });

        print("Fetched orders for user $userId:");
        for (var order in orders) {
          print("Order ID: ${order.idForOrder}");
        }
      } else {
        print("Failed to fetch orders: ${response.statusCode}");
      }
    } else {
      print("User ID not found in SharedPreferences.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Order Details"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: orders.isNotEmpty
            ? ListView.builder(
                itemCount: orders.length,
                itemBuilder: (context, index) {
                  return OrderCard(order: orders[index]);
                },
              )
            : const Center(child: CircularProgressIndicator()),
      ),
    );
  }
}
