import 'package:flutter/material.dart';
import 'package:whatsup/calls_screen.dart';
import 'package:whatsup/communities_screen.dart';
import 'package:whatsup/settings_screen.dart';
import 'package:whatsup/update_screen.dart';
import 'package:whatsup/users.dart';

class homePage extends StatefulWidget {
  const homePage({super.key});

  @override
  State<homePage> createState() => _homePageState();
}

class _homePageState extends State<homePage> {
  int selectedIndex = 3;
  List<User> users = [
    User(
        id: 0,
        name: "Sara Ahmed",
        path: "assets/images/1.png",
        message: "Hi Sara! How's it going?",
        time: "10:30 AM"),
    User(
        id: 1,
        name: "Omar Ali",
        path: "assets/images/2.png",
        message: "Hey Omar, let's catch up soon!",
        time: "11:00 AM"),
    User(
        id: 2,
        name: "Maya Hassan",
        path: "assets/images/3.png",
        message: "What's up, Maya? Long time no see!",
        time: "2:15 PM"),
    User(
        id: 3,
        name: "Lina Khaled",
        path: "assets/images/4.png",
        message: "Hi Lina, hope you're having a great day!",
        time: "4:00 PM"),
    User(
        id: 4,
        name: "Yousef Tariq",
        path: "assets/images/5.png",
        message: "Yo Yousef, did you check that out?",
        time: "5:45 PM"),
    User(
        id: 5,
        name: "Nour Salim",
        path: "assets/images/6.png",
        message: "Hello Nour, looking forward to our plans!",
        time: "7:30 PM"),
    User(
        id: 6,
        name: "Tariq Jamil",
        path: "assets/images/7.png",
        message: "Hey Tariq, let's meet up soon!",
        time: "9:00 AM"),
    User(
        id: 7,
        name: "Amina Youssef",
        path: "assets/images/8.png",
        message: "Amina, we should catch up!",
        time: "12:45 PM"),
    User(
        id: 8,
        name: "Rami Zayed",
        path: "assets/images/9.png",
        message: "Rami, how's your new project going?",
        time: "3:30 PM"),
    User(
        id: 9,
        name: "Farah Noor",
        path: "assets/images/10.png",
        message: "Hi Farah! Let's hang out this weekend.",
        time: "6:15 PM"),
    User(
        id: 10,
        name: "Jad Khoury",
        path: "assets/images/11.png",
        message: "Jad, I have some cool ideas for us!",
        time: "8:00 PM"),
  ];
  @override
  Widget build(BuildContext context) {
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
                Row(
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
                            Icons.camera_alt,
                          ),
                          padding: EdgeInsets.zero,
                          alignment: Alignment.center,
                        ),
                      ),
                    ),
                    SizedBox(
                      width: 5,
                    ),
                    Container(
                      height: 30,
                      width: 30,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(30),
                        color: Colors.green[700],
                      ),
                      child: Center(
                        child: IconButton(
                          onPressed: () {},
                          icon: Icon(
                            Icons.add,
                            color: Colors.white,
                          ),
                          padding: EdgeInsets.zero,
                          alignment: Alignment.center,
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
            Container(
              alignment: Alignment.topLeft,
              child: Text(
                "Chats",
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 35),
              ),
            ),
            Container(
              padding: EdgeInsets.all(8),
              decoration: BoxDecoration(
                color: const Color.fromARGB(73, 158, 158, 158),
                borderRadius: BorderRadius.circular(10),
              ),
              child: Row(
                children: [Icon(Icons.search), Text("Search")],
              ),
            ),
            Container(
              margin: EdgeInsets.only(top: 10),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Container(
                    height: 30,
                    width: 40,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(30),
                      color: Colors.green[100],
                    ),
                    child: Center(child: Text("All")),
                  ),
                  Container(
                    height: 30,
                    width: 70,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(30),
                      color: Colors.grey[300],
                    ),
                    child: Center(child: Text("Unread")),
                  ),
                  Container(
                    height: 30,
                    width: 80,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(30),
                      color: Colors.grey[300],
                    ),
                    child: Center(child: Text("Favorites")),
                  ),
                  Container(
                    height: 30,
                    width: 80,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(30),
                      color: Colors.grey[300],
                    ),
                    child: Center(child: Text("Groups")),
                  ),
                  Container(
                    height: 30,
                    width: 35,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(30),
                      color: Colors.grey[300],
                    ),
                    child: Icon(Icons.add),
                  ),
                ],
              ),
            ),
            Expanded(
              child: ListView.builder(
                itemCount: users.length,
                itemBuilder: (context, index) {
                  return Column(
                    children: [
                      SizedBox(
                        height: 10,
                      ),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Container(
                            margin: EdgeInsets.all(2),
                            width: 77,
                            height: 77,
                            decoration: BoxDecoration(
                              shape: BoxShape.circle,
                              image: DecorationImage(
                                  image: AssetImage(users[index].path),
                                  fit: BoxFit.cover),
                            ),
                          ),
                          SizedBox(width: 10),
                          Expanded(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    Text(
                                      users[index].name,
                                      style: TextStyle(
                                          fontWeight: FontWeight.bold,
                                          fontSize: 18),
                                    ),
                                    Text(
                                      users[index].time,
                                      style: TextStyle(
                                        color: Colors.grey,
                                        fontSize: 16,
                                      ),
                                    ),
                                  ],
                                ),
                                SizedBox(height: 5),
                                Text(
                                  users[index].message,
                                  style: TextStyle(
                                      color: Colors.grey, fontSize: 18),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                      const Padding(
                        padding: EdgeInsets.only(left: 100),
                        child: Divider(),
                      ),
                    ],
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
