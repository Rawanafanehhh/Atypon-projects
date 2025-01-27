import 'package:flutter/material.dart';
import 'package:whatsup/calls_screen.dart';
import 'package:whatsup/communities_screen.dart';
import 'package:whatsup/home_screen.dart';
import 'package:whatsup/option_settings.dart';
import 'package:whatsup/update_screen.dart';

class SettingsScreen extends StatefulWidget {
  @override
  State<SettingsScreen> createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  int selectedIndex = 4;
  List<OptionTile> option1 = [
    OptionTile(title: 'Lists', icon: Icons.list_alt),
    OptionTile(title: 'Broadcast messages', icon: Icons.campaign),
    OptionTile(title: 'Starred messages', icon: Icons.star_border),
    OptionTile(title: 'Linked devices', icon: Icons.devices),
  ];
  List<OptionTile> option2 = [
    OptionTile(title: 'Account', icon: Icons.person_outline),
    OptionTile(title: 'Privacy', icon: Icons.lock_outline),
    OptionTile(title: 'Chats', icon: Icons.chat_bubble_outline),
    OptionTile(title: 'Notifications', icon: Icons.notifications_none),
    OptionTile(title: 'Storage and data', icon: Icons.storage),
  ];
  List<OptionTile> option3 = [
    OptionTile(title: 'Help', icon: Icons.help),
    OptionTile(title: 'Invite a friend', icon: Icons.people),
  ];
  List<OptionTile> option4 = [
    OptionTile(title: 'Open Instagram', icon: Icons.apple),
    OptionTile(title: 'Open Facebook', icon: Icons.facebook),
    OptionTile(title: 'Open Threads', icon: Icons.apple),
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
      body: SingleChildScrollView(
        child: Padding(
          padding:
              const EdgeInsets.only(top: 50, right: 20, left: 20, bottom: 20),
          child: Column(
            children: [
              Container(
                alignment: Alignment.topLeft,
                child: Text(
                  "Settings",
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
              SizedBox(
                height: 15,
              ),
              Column(
                children: [
                  Container(
                    padding: EdgeInsets.all(15),
                    decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.only(
                            topLeft: Radius.circular(15),
                            topRight: Radius.circular(15))),
                    child: Row(
                      children: [
                        CircleAvatar(
                          radius: 30,
                          backgroundImage: AssetImage('assets/images/13.jpg'),
                        ),
                        SizedBox(width: 16),
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              'Rawan Afaneh',
                              style: TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            SizedBox(height: 4),
                            Text(
                              '.',
                              style: TextStyle(color: Colors.grey),
                            ),
                          ],
                        ),
                        Spacer(),
                        Container(
                          height: 30,
                          width: 30,
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(30),
                            color: Color.fromARGB(255, 226, 226, 226),
                          ),
                          child: Center(
                            child: IconButton(
                              onPressed: () {},
                              icon: Icon(Icons.qr_code, color: Colors.black),
                              padding: EdgeInsets.zero,
                              alignment: Alignment.center,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.all(15),
                    decoration: BoxDecoration(
                        border: Border(
                            top: BorderSide(
                                color:
                                    const Color.fromARGB(255, 210, 210, 210))),
                        color: Colors.white,
                        borderRadius: BorderRadius.only(
                            bottomLeft: Radius.circular(15),
                            bottomRight: Radius.circular(15))),
                    child: Row(
                      children: [
                        Icon(Icons.person),
                        SizedBox(
                          width: 15,
                        ),
                        Text(
                          "Avatar",
                          style: TextStyle(fontSize: 17),
                        ),
                        Spacer(),
                        Icon(Icons.arrow_forward_ios_sharp)
                      ],
                    ),
                  ),
                  options(option1, 250),
                  options(option2, 300),
                  options(option3, 150),
                  Container(
                      alignment: Alignment.topLeft,
                      padding: EdgeInsets.only(left: 20),
                      child: Text("Also from Meta")),
                  options(option4, 200),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  Container options(List option, double height) {
    return Container(
      height: height,
      child: ListView.builder(
        shrinkWrap: true,
        physics: NeverScrollableScrollPhysics(),
        itemCount: option.length,
        itemBuilder: (context, index) {
          return Container(
            padding: EdgeInsets.all(15),
            decoration: BoxDecoration(
              border: Border(
                bottom: BorderSide(
                  color: const Color.fromARGB(255, 210, 210, 210),
                ),
              ),
              color: Colors.white,
            ),
            child: Row(
              children: [
                Icon(option[index].icon),
                SizedBox(
                  width: 15,
                ),
                Text(
                  option[index].title,
                  style: TextStyle(fontSize: 17),
                ),
                Spacer(),
                Icon(Icons.arrow_forward_ios_sharp),
              ],
            ),
          );
        },
      ),
    );
  }
}
