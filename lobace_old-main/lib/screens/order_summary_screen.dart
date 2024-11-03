import 'dart:math';
import 'package:flutter/material.dart';
import 'package:food/models/food.dart';
import 'package:food/models/food_card_new.dart';
import 'package:food/models/food_cart_provider.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';

class OrderSummaryScreen extends StatefulWidget {
  const OrderSummaryScreen({Key? key}) : super(key: key);

  @override
  State<OrderSummaryScreen> createState() => _OrderSummaryScreenState();
}

class _OrderSummaryScreenState extends State<OrderSummaryScreen> {
  List<Food> foodsList = [];

  @override
  void initState() {
    super.initState();
    fetchFoodDetails(context);
  }

  Future<void> fetchFoodDetails(BuildContext context) async {
    final foodCounts = context.read<FoodCartProvider>().foodCounts;
    for (var entry in foodCounts.entries.where((entry) => entry.value > 0)) {
      final foodId = entry.key;
      findFoodById(foodId, context);
    }
  }

  Future<bool> findFoodById(int id, BuildContext context) async {
    var uri = Uri.parse("http://10.0.2.2:8080/food/find-by-id/$id");
    Map<String, String> header = {"Content-Type": "application/json"};
    var response = await http.post(uri, headers: header);
    var parsedJson = jsonDecode(response.body);
    Food food = Food.fromJson(parsedJson);

    setState(() {
      foodsList.add(food);
    });

    return true;
  }

  @override
  Widget build(BuildContext context) {
    final foodCounts = context.watch<FoodCartProvider>().foodCounts;

    return Scaffold(
      appBar: AppBar(
        title: const Text("Order Summary"),
        backgroundColor: const Color(0xFF9139BA),
        elevation: 0,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const Text(
              "Your Selected Items",
              style: TextStyle(
                fontSize: 22,
                fontWeight: FontWeight.bold,
              ),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 10),
            foodsList.isEmpty
                ? const Center(child: CircularProgressIndicator())
                : ListView.builder(
                    physics: const NeverScrollableScrollPhysics(),
                    shrinkWrap: true,
                    itemCount: foodsList.length,
                    itemBuilder: (context, index) {
                      final food = foodsList[index];
                      return Card(
                        margin: const EdgeInsets.symmetric(vertical: 8.0),
                        child: ListTile(
                          leading: CircleAvatar(
                            backgroundImage: NetworkImage(food.image),
                          ),
                          title: Text(
                            food.name,
                            style: const TextStyle(
                              fontWeight: FontWeight.w600,
                              fontSize: 16,
                            ),
                          ),
                          subtitle: Text(
                              "Quantity: ${foodCounts[food.id]}"),
                        ),
                      );
                    },
                  ),
            const SizedBox(height: 30),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                padding: const EdgeInsets.symmetric(vertical: 14),
                backgroundColor: const Color(0xFF9139BA),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
              ),
              onPressed: () {
                makeOrder(context);
              },
              child: const Text(
                "Make Order",
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.white,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

Future<bool> makeOrder(BuildContext context) async {
  SharedPreferences prefs = await SharedPreferences.getInstance();
  int? userId = prefs.getInt('userId');
  final foodCounts = context.read<FoodCartProvider>().foodCounts;

  var uri = Uri.parse("http://10.0.2.2:8080/order/create-order");
  Map<String, String> headers = {"Content-Type": "application/json"};

  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
  final random = Random();
  String uniqueId = List.generate(
      20, (index) => characters[random.nextInt(characters.length)]).join();

  for (var entry in foodCounts.entries) {
    final body = jsonEncode({
      'foodId': entry.key,
      'quantity': entry.value,
      'userId': userId,
      'idForOrder': uniqueId,
    });

    await http.post(uri, headers: headers, body: body);
  }

  return true;
}
