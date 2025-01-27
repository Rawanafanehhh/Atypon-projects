import 'package:flutter/material.dart';
import 'package:whatsup/call_settings.dart';
import 'package:whatsup/communities_screen.dart';
import 'package:whatsup/home_screen.dart';
import 'package:whatsup/settings_screen.dart';
import 'package:whatsup/update_screen.dart';

class CallsScreen extends StatefulWidget {
  @override
  State<CallsScreen> createState() => _CallsScreenState();
}

class _CallsScreenState extends State<CallsScreen> {
  final List<CallSettings> callData = [
    CallSettings(
      name: "Sara Ahmed",
      status: "(2) Missed",
      date: "25/12/2024",
      missed: "true",
      image: "assets/images/1.png",
    ),
    CallSettings(
      name: "Omar Ali",
      status: "Outgoing",
      date: "25/12/2024",
      missed: "false",
      image: "assets/images/2.png",
    ),
    CallSettings(
      name: "Maya Hassan",
      status: "Missed",
      date: "24/12/2024",
      missed: "true",
      image: "assets/images/3.png",
    ),
    CallSettings(
      name: "Lina Khaled❤\ufe0f",
      status: "Missed",
      date: "08/12/2024",
      missed: "true",
      image: "assets/images/4.png",
    ),
    CallSettings(
      name: "Yousef Tariq",
      status: "(2) Missed",
      date: "20/11/2024",
      missed: "true",
      image: "assets/images/5.png",
    ),
  ];

  @override
  Widget build(BuildContext context) {
    int selectedIndex = 1;
    return Scaffold(
      backgroundColor: Color.fromARGB(255, 247, 247, 247),
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
      body: Padding(
        padding:
            const EdgeInsets.only(top: 30, right: 20, left: 20, bottom: 20),
        child: Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Container(
                  height: 30,
                  width: 30,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(30),
                    color: Color.fromARGB(255, 238, 238, 238),
                  ),
                  child: Center(
                    child: IconButton(
                      onPressed: () {},
                      icon: Icon(
                        Icons.more_horiz,
                      ),
                      padding: EdgeInsets.zero,
                      alignment: Alignment.center,
                    ),
                  ),
                ),
                Container(
                  padding: EdgeInsets.only(left: 25),
                  decoration: BoxDecoration(
                      border: const Border(
                          bottom: BorderSide(
                              color: Color.fromARGB(255, 201, 200, 200),
                              width: 2),
                          top: BorderSide(
                              color: Color.fromARGB(255, 201, 200, 200),
                              width: 2),
                          left: BorderSide(
                              color: Color.fromARGB(255, 201, 200, 200),
                              width: 2)),
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(5)),
                  child: Row(
                    children: [
                      Text("All      "),
                      Container(
                        padding: EdgeInsets.all(5),
                        decoration: BoxDecoration(
                            color: const Color.fromARGB(255, 201, 200, 200),
                            borderRadius: BorderRadius.only(
                                topRight: Radius.circular(5),
                                bottomRight: Radius.circular(5))),
                        child: Text("  Missed  "),
                      )
                    ],
                  ),
                ),
                Container(
                  height: 30,
                  width: 30,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(30),
                    color: Color.fromARGB(255, 238, 238, 238),
                  ),
                  child: Center(
                    child: IconButton(
                      onPressed: () {},
                      icon: Icon(
                        Icons.add,
                      ),
                      padding: EdgeInsets.zero,
                      alignment: Alignment.center,
                    ),
                  ),
                ),
              ],
            ),
            Container(
              alignment: Alignment.topLeft,
              child: Text(
                "Calls",
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 35),
              ),
            ),
            SizedBox(
              height: 15,
            ),
            Container(
              alignment: Alignment.topLeft,
              child: Text(
                "Favorites",
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
              ),
            ),
            Container(
              margin: EdgeInsets.all(5),
              padding:
                  EdgeInsets.only(top: 10, left: 30, right: 30, bottom: 10),
              decoration: BoxDecoration(
                  color: Colors.white, borderRadius: BorderRadius.circular(10)),
              child: Row(
                children: [
                  Container(
                    height: 40,
                    width: 40,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(30),
                      color: Color.fromARGB(255, 238, 238, 238),
                    ),
                    child: Center(
                      child: IconButton(
                        onPressed: () {},
                        icon: Icon(
                          Icons.add,
                        ),
                        padding: EdgeInsets.zero,
                        alignment: Alignment.center,
                      ),
                    ),
                  ),
                  SizedBox(
                    width: 15,
                  ),
                  Text(
                    "Add favorite",
                    style: TextStyle(fontSize: 17),
                  ),
                ],
              ),
            ),
            SizedBox(
              height: 10,
            ),
            Container(
              alignment: Alignment.topLeft,
              child: Text(
                "Recent",
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
              ),
            ),
            Expanded(
              child: ListView.builder(
                itemCount: callData.length,
                itemBuilder: (context, index) {
                  final call = callData[index];
                  return Container(
                    padding: EdgeInsets.only(top: 5, bottom: 5),
                    decoration: BoxDecoration(
                        color: Colors.white,
                        border: Border(
                            bottom: BorderSide(
                                color:
                                    const Color.fromARGB(255, 207, 206, 206)))),
                    child: callTile(call.name, call.status, call.date,
                        call.missed == "true", call.image),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget callTile(
      String name, String status, String date, bool missed, String image) {
    return ListTile(
        leading: Container(
          margin: EdgeInsets.all(2),
          width: 60,
          height: 60,
          decoration: BoxDecoration(
            shape: BoxShape.circle,
            image: DecorationImage(image: AssetImage(image), fit: BoxFit.cover),
          ),
        ),
        title: Text(
          name,
          style: TextStyle(
            fontWeight: FontWeight.bold,
            color: missed ? Colors.red : Colors.black,
          ),
        ),
        subtitle: Text(status),
        trailing: TextButton.icon(
            onPressed: () {},
            icon: Icon(Icons.info, color: Colors.grey, size: 16),
            label: Text(
              date,
              style: TextStyle(color: Colors.grey),
            )));
  }
}
