import 'package:flutter/material.dart';
import 'package:whatsup/calls_screen.dart';
import 'package:whatsup/communities_screen.dart';
import 'package:whatsup/home_screen.dart';
import 'package:whatsup/settings_screen.dart';
import 'package:whatsup/update_settings.dart';

class UpdatesScreen extends StatefulWidget {
  @override
  State<UpdatesScreen> createState() => _UpdatesScreenState();
}

class _UpdatesScreenState extends State<UpdatesScreen> {
  final List<UpdateSettings> recentUpdates = [
    UpdateSettings(
      name: "Sara Ahmed",
      time: "5h ago",
      image: "assets/images/1.png",
    ),
    UpdateSettings(
      name: "Omar Ali",
      time: "10h ago",
      image: "assets/images/2.png",
    ),
    UpdateSettings(
      name: "Maya Hassan",
      time: "15h ago",
      image: "assets/images/3.png",
    ),
    UpdateSettings(
      name: "Lina Khaled",
      time: "21h ago",
      image: "assets/images/4.png",
    ),
  ];

  @override
  Widget build(BuildContext context) {
    int selectedIndex = 0;
    return Scaffold(
      backgroundColor: Color.fromARGB(255, 247, 247, 247),
      body: Padding(
        padding: const EdgeInsets.only(top: 30, bottom: 20),
        child: Column(
          children: [
            Container(
              alignment: Alignment.topLeft,
              child: Padding(
                padding: const EdgeInsets.only(
                  right: 20,
                  left: 20,
                ),
                child: Column(
                  children: [
                    Container(
                      height: 30,
                      width: 30,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(30),
                        color: Color.fromARGB(255, 238, 238, 238),
                      ),
                      child: IconButton(
                        onPressed: () {},
                        icon: Icon(
                          Icons.more_horiz,
                        ),
                        padding: EdgeInsets.zero,
                        alignment: Alignment.center,
                      ),
                    ),
                    Text(
                      "Updates",
                      style:
                          TextStyle(fontWeight: FontWeight.bold, fontSize: 35),
                    ),
                  ],
                ),
              ),
            ),
            SizedBox(
              height: 10,
            ),
            Container(
              padding: const EdgeInsets.only(
                right: 20,
                left: 20,
              ),
              alignment: Alignment.topLeft,
              child: Text(
                "Status",
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 24),
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(top: 16.0, bottom: 16),
              child: Container(
                padding: EdgeInsets.all(15),
                decoration: BoxDecoration(
                  color: Colors.white,
                ),
                child: Row(
                  children: [
                    CircleAvatar(
                      child: Icon(Icons.add),
                      radius: 30,
                    ),
                    SizedBox(width: 16),
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'My status',
                            style: TextStyle(
                                fontSize: 16, fontWeight: FontWeight.bold),
                          ),
                          Text('Add to my status',
                              style: TextStyle(color: Colors.grey)),
                        ],
                      ),
                    ),
                    IconButton(
                      icon: Icon(Icons.camera_alt_rounded, color: Colors.black),
                      onPressed: () {},
                    ),
                    IconButton(
                      icon: Icon(Icons.edit, color: Colors.black),
                      onPressed: () {},
                    ),
                  ],
                ),
              ),
            ),
            Container(
              padding: const EdgeInsets.only(
                right: 20,
                left: 20,
              ),
              alignment: Alignment.topLeft,
              child: Text(
                "Recent updates",
                style: TextStyle(fontSize: 15),
              ),
            ),
            Expanded(
              child: ListView.builder(
                itemCount: recentUpdates.length,
                itemBuilder: (context, index) {
                  final update = recentUpdates[index];
                  return Container(
                    decoration: BoxDecoration(
                      color: Colors.white,
                    ),
                    child: Column(
                      children: [
                        ListTile(
                          leading: Container(
                            width: 60,
                            height: 60,
                            decoration: BoxDecoration(
                              shape: BoxShape.circle,
                              border: Border.all(
                                color: Colors.green,
                                width: 3,
                              ),
                            ),
                            child: CircleAvatar(
                              radius: 30,
                              backgroundImage: AssetImage(update.image),
                            ),
                          ),
                          title: Text(update.name,
                              style: TextStyle(fontWeight: FontWeight.bold)),
                          subtitle: Text(update.time),
                          onTap: () {},
                        ),
                        Padding(
                          padding: const EdgeInsets.only(left: 80.0),
                          child: Divider(),
                        )
                      ],
                    ),
                  );
                },
              ),
            ),
          ],
        ),
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
