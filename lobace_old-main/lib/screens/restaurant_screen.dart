import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:food/models/food.dart';
import 'package:food/models/food_card.dart';
import 'package:food/models/food_cart_provider.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';

class RestaurantScreen extends StatefulWidget {
  final int id;

  RestaurantScreen({required this.id});

  @override
  State<RestaurantScreen> createState() => _RestaurantScreenState();
}

class _RestaurantScreenState extends State<RestaurantScreen> {
  List<Food> foods = [];
  Map<int, int> foodCounts = {};

  @override
  void initState() {
    super.initState();
    getFoodsRestaurantById(widget.id, context);
  }

  Future<bool> getFoodsRestaurantById(int id, BuildContext context) async {
    var uri = Uri.parse("http://10.0.2.2:8080/food/find-foods-by-restaurant-id/$id");
    Map<String, String> headers = {"Content-Type": "application/json"};
    var response = await http.post(uri, headers: headers);

    List jsonResponse = jsonDecode(response.body);
    var foodList = jsonResponse.map((food) => Food.fromJson(food)).toList();

    setState(() {
      foods = foodList;
      foodCounts = {for (var food in foods) food.id: 0};
    });

    return true;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[100],
      appBar: AppBar(
        backgroundColor: Colors.deepPurple,
        title: const Text("Lobace Food"),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(15.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              "Popular Dishes",
              style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 10),
            Expanded(
              child: ListView.builder(
                itemCount: foods.length,
                itemBuilder: (context, index) {
                  final food = foods[index];
                  final imageBytes = base64Decode(food.image);
                  return Card(
                    margin: const EdgeInsets.symmetric(vertical: 8.0),
                    elevation: 2,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Padding(
                      padding: const EdgeInsets.all(12.0),
                      child: Row(
                        children: [
                          // Food image placeholder
                          Container(
                            width: 80,
                            height: 80,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(8),
                              image: DecorationImage(
                                image: NetworkImage(food.image),
                                fit: BoxFit.cover,
                              ),
                            ),
                          ),
                          const SizedBox(width: 15),
                          // Food name and price
                          Expanded(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  food.name,
                                  style: const TextStyle(
                                    fontSize: 18,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                const SizedBox(height: 5),
                                Text(
                                  "\$${food.price}",
                                  style: TextStyle(
                                    fontSize: 16,
                                    color: Colors.grey[700],
                                  ),
                                ),
                              ],
                            ),
                          ),
                          // Quantity controls
                          Row(
                            children: [
                              IconButton(
                                icon: const Icon(Icons.remove, color: Colors.deepPurple),
                                onPressed: () {
                                  context.read<FoodCartProvider>().decrementFoodCount(food.id);
                                },
                              ),
                              Text(
                                '${context.watch<FoodCartProvider>().foodCounts[food.id] ?? 0}',
                                style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                              ),
                              IconButton(
                                icon: const Icon(Icons.add, color: Colors.deepPurple),
                                onPressed: () {
                                  context.read<FoodCartProvider>().incrementFoodCount(food.id);
                                },
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.pushNamed(context, '/cartScreen');
        },
        backgroundColor: Colors.deepPurple,
        child: const Icon(Icons.shopping_cart, color: Colors.white),
      ),
    );
  }
}
