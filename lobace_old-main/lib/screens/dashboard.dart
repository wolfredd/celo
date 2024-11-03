import 'package:flutter/material.dart';
// import 'package:food/models/restaurant.dart';
// import 'package:food/models/restaurant_card.dart';
import 'package:food/models/restuarant.dart';
import 'package:food/models/restuarant_card.dart';
import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class Dashboard extends StatefulWidget {
  const Dashboard({super.key});

  @override
  State<Dashboard> createState() => _DashboardState();
}

class _DashboardState extends State<Dashboard> {
  List<Restaurant> restaurants = [];

  @override
  void initState() {
    super.initState();
    retrieveAllRestaurants(context);
  }

  Future<void> retrieveAllRestaurants(BuildContext context) async {
    var uri = Uri.parse("http://10.0.2.2:8080/restaurant/find-all-restaurants");
    var header = {"Content-Type": "application/json"};
    var response = await http.post(uri, headers: header);

    List jsonResponse = json.decode(response.body);
    setState(() {
      restaurants = jsonResponse
          .map((restaurant) => Restaurant.fromJson(restaurant))
          .toList();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[200],
      appBar: AppBar(
        backgroundColor: Colors.deepPurple,
        elevation: 2,
        title: const Text(
          "Lobace Food",
          style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
        ),
        actions: [
          IconButton(
            icon: const Icon(Icons.shopping_cart, color: Colors.white),
            onPressed: () {},
          ),
        ],
      ),
      drawer: Drawer(
        child: Column(
          children: [
            const DrawerHeader(
              decoration: BoxDecoration(color: Colors.deepPurple),
              child: Center(
                child: Text("Menu", style: TextStyle(color: Colors.white, fontSize: 24)),
              ),
            ),
            _drawerTile(Icons.person, "Update Profile", () {
              Navigator.pushNamed(context, '/updateProfileScreen');
            }),
            _drawerTile(Icons.delete, "Delete Profile", () {
              Navigator.pushNamed(context, '/deleteProfileScreen');
            }),
            _drawerTile(Icons.delivery_dining, "Order Online", () {}),
            _drawerTile(Icons.menu_book, "Menu", () {}),
            _drawerTile(Icons.track_changes, "Tracker", () {
              Navigator.pushNamed(context, '/tracker');
            }),
            _drawerTile(Icons.receipt, "Orders", () {
              Navigator.pushNamed(context, '/orderSummary');
            }),
            _drawerTile(Icons.contact_phone, "Contact", () {}),
            _drawerTile(Icons.info, "About Us", () {}),
          ],
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            _sectionTitle("What do you need?"),
            const SizedBox(height: 10),
            _buildSearchField(),
            const SizedBox(height: 20),
            _sectionTitle("Restaurants"),
            const SizedBox(height: 10),
            Expanded(
              child: ListView.builder(
                itemCount: restaurants.length,
                itemBuilder: (context, index) {
                  return RestaurantCard(
                    restaurantId: restaurants[index].id,
                    restaurantName: restaurants[index].name,
                    restaurantImage: restaurants[index].image,
                    restaurantCategory: restaurants[index].category,
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _drawerTile(IconData icon, String title, VoidCallback onTap) {
    return ListTile(
      leading: Icon(icon, color: Colors.deepPurple),
      title: Text(title, style: const TextStyle(fontWeight: FontWeight.w600)),
      onTap: onTap,
    );
  }

  Widget _sectionTitle(String title) {
    return Row(
      children: [
        Text(
          title,
          style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
        ),
      ],
    );
  }

  Widget _buildSearchField() {
    return TextField(
      decoration: InputDecoration(
        hintText: "Search for dishes or restaurants",
        prefixIcon: Icon(Icons.search, color: Colors.deepPurple),
        filled: true,
        fillColor: Colors.white,
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10.0),
          borderSide: BorderSide.none,
        ),
      ),
    );
  }
}
