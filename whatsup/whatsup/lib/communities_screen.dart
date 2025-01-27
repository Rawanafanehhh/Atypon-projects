import 'package:flutter/material.dart';
import 'package:whatsup/calls_screen.dart';
import 'package:whatsup/home_screen.dart';
import 'package:whatsup/settings_screen.dart';
import 'package:whatsup/update_screen.dart';

class CommunitiesScreen extends StatefulWidget {
  @override
  State<CommunitiesScreen> createState() => _CommunitiesScreenState();
}

class _CommunitiesScreenState extends State<CommunitiesScreen> {
  int selectedIndex = 2;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromARGB(255, 247, 247, 247),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.only(top: 100, right: 20, left: 20),
            child: Column(
              children: [
                Container(
                  alignment: Alignment.topLeft,
                  child: Text(
                    "Communities",
                    style: TextStyle(fontWeight: FontWeight.bold, fontSize: 35),
                  ),
                ),
                Image.asset(
                  'assets/images/12.png',
                  height: 200,
                ),
                SizedBox(height: 20),
                Container(
                  alignment: Alignment.topLeft,
                  child: Text(
                    "Stay connected with a community",
                    style: TextStyle(fontWeight: FontWeight.w400, fontSize: 20),
                  ),
                ),
                SizedBox(height: 10),
                Text(
                  'Communities bring members together in topic-based groups. Any community you’re added to will appear here.',
                  textAlign: TextAlign.start,
                  style: TextStyle(fontSize: 17, color: Colors.grey[700]),
                ),
                SizedBox(height: 20),
                TextButton(
                  onPressed: () {},
                  child: Text(
                    'See example communities',
                    style: TextStyle(color: Colors.green, fontSize: 14),
                  ),
                ),
                SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {},
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.green,
                    padding:
                        EdgeInsets.symmetric(horizontal: 100, vertical: 12),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(15),
                    ),
                  ),
                  child: Text(
                    '+ New community',
                    style: TextStyle(color: Colors.white, fontSize: 16),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        backgroundColor: Color.fromARGB(255, 247, 247, 247),
        currentIndex: selectedIndex,
        onTap: (index) {
          setState(() {
            selectedIndex = index;
          });

          switch (index) {
            case 0:
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(builder: (context) => UpdatesScreen()),
              );
              break;
            case 1:
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(builder: (context) => CallsScreen()),
              );
              break;
            case 2:
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(builder: (context) => CommunitiesScreen()),
              );
              break;
            case 3:
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(builder: (context) => homePage()),
              );
              break;
            case 4:
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(builder: (context) => SettingsScreen()),
              );
              break;
          }
        },
        selectedItemColor: Colors.black,
        unselectedItemColor: Colors.grey,
        iconSize: 30,
        type: BottomNavigationBarType.fixed,
        items: [
          BottomNavigationBarItem(
            icon: selectedIndex == 0
                ? Icon(Icons.update)
                : Icon(Icons.update_outlined),
            label: 'Updates',
          ),
          BottomNavigationBarItem(
            icon: selectedIndex == 1
                ? Icon(Icons.call)
                : Icon(Icons.call_outlined),
            label: 'Calls',
          ),
          BottomNavigationBarItem(
            icon: selectedIndex == 2
                ? Icon(Icons.groups)
                : Icon(Icons.groups_outlined),
            label: 'Communities',
          ),
          BottomNavigationBarItem(
            icon: selectedIndex == 3
                ? Icon(Icons.chat)
                : Icon(Icons.chat_outlined),
            label: 'Chats',
          ),
          BottomNavigationBarItem(
            icon: selectedIndex == 4
                ? Icon(Icons.settings)
                : Icon(Icons.settings_outlined),
            label: 'Settings',
          ),
        ],
      ),
    );
  }
}
