import 'package:flutter/material.dart';
import 'package:signup/welcom.dart';

class SignupPage extends StatefulWidget {
  const SignupPage({super.key});

  @override
  State<SignupPage> createState() => _SignupPageState();
}

class _SignupPageState extends State<SignupPage> {
  bool showErrorEmail = false;
  bool showErrorName = false;
  bool showErrorPassword = false;
  bool showErrorNotMatch = false;
  bool obscureText1 = true;
  bool obscureText2 = true;

  TextEditingController nameController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController confirmPasswordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromARGB(255, 244, 244, 242),
      body: SingleChildScrollView(
        child: Container(
          padding: const EdgeInsets.all(50),
          child: Column(
            children: [
              Image.asset("assets/images/1.png"),
              const Text(
                "Sign Up",
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 30,
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(left: 10, right: 10),
                child: Column(
                  children: [
                    SizedBox(
                      height: 20,
                    ),
                    TextFormField(
                      onChanged: (value) {
                        showErrorName = nameController.text.isEmpty;
                        setState(() {});
                      },
                      controller: nameController,
                      decoration: InputDecoration(
                        label: const Text("name"),
                        prefixIcon: Icon(
                          Icons.abc,
                          color: Colors.grey,
                        ),
                        errorText: showErrorName ? "Name is requierd" : null,
                      ),
                    ),
                    const SizedBox(height: 20),
                    TextFormField(
                      onChanged: (value) {
                        showErrorEmail = !isEmail(
                          email: emailController.text,
                        );
                        setState(() {});
                      },
                      keyboardType: TextInputType.emailAddress,
                      controller: emailController,
                      decoration: InputDecoration(
                        label: const Text("email"),
                        prefixIcon: Icon(
                          Icons.email,
                          color: Colors.grey,
                        ),
                        hintText: "gemo@demo.com",
                        hintStyle: TextStyle(
                            color: const Color.fromARGB(255, 190, 190, 190)),
                        errorText: showErrorEmail ? "Enter Valid Email" : null,
                      ),
                    ),
                    const SizedBox(height: 20),
                    TextFormField(
                      onChanged: (value) {
                        showErrorPassword = !isStrongPassword(
                          password: passwordController.text,
                        );
                        setState(() {});
                      },
                      obscureText: obscureText1,
                      controller: passwordController,
                      decoration: InputDecoration(
                        label: const Text("password"),
                        prefixIcon: Icon(
                          Icons.password,
                          color: Colors.grey,
                        ),
                        suffixIcon: IconButton(
                          onPressed: () {
                            obscureText1 = !obscureText1;
                            setState(() {});
                          },
                          icon: Icon(
                            obscureText1
                                ? Icons.visibility
                                : Icons.visibility_off,
                          ),
                        ),
                        errorText: showErrorPassword
                            ? "At least 8 characters long.\n•⁠ Should include:\n- At least one uppercase letter.\n- At least one lowercase letter.\n- At least one number.\n- At least one special character "
                            : null,
                      ),
                    ),
                    const SizedBox(height: 20),
                    TextFormField(
                      onChanged: (value) {
                        showErrorNotMatch = !isMatch(
                            password: passwordController.text,
                            conPassowrd: confirmPasswordController.text);
                        setState(() {});
                      },
                      obscureText: obscureText2,
                      controller: confirmPasswordController,
                      decoration: InputDecoration(
                        label: const Text("confirm password"),
                        prefixIcon: Icon(
                          Icons.password,
                          color: Colors.grey,
                        ),
                        suffixIcon: IconButton(
                          onPressed: () {
                            obscureText2 = !obscureText2;
                            setState(() {});
                          },
                          icon: Icon(
                            obscureText2
                                ? Icons.visibility
                                : Icons.visibility_off,
                          ),
                        ),
                        errorText: showErrorNotMatch
                            ? "The passwords you entered do not match."
                            : null,
                      ),
                    ),
                    const SizedBox(height: 30),
                    Container(
                      width: 200,
                      decoration: BoxDecoration(
                        color: Colors.black,
                        borderRadius: BorderRadius.circular(15),
                      ),
                      child: TextButton(
                        onPressed: () {
                          showErrorName = nameController.text.isEmpty;
                          showErrorEmail = !isEmail(
                            email: emailController.text,
                          );
                          showErrorPassword = !isStrongPassword(
                            password: passwordController.text,
                          );
                          showErrorNotMatch = !isMatch(
                              password: passwordController.text,
                              conPassowrd: confirmPasswordController.text);
                          !showErrorEmail &&
                                  !showErrorName &&
                                  !showErrorPassword &&
                                  !showErrorNotMatch
                              ? Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => WelcomeScreen(),
                                  ))
                              : null;

                          setState(() {});
                        },
                        child: const Text(
                          "Sign Up",
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 20,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  bool isStrongPassword({required String password}) {
    String pattern =
        r'^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#\$&*~]).{8,}$';
    RegExp regExp = RegExp(pattern);
    return regExp.hasMatch(password);
  }

  bool isEmail({required String email}) {
    String p =
        r'^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$';
    RegExp regExp = RegExp(p);
    return regExp.hasMatch(email);
  }

  bool isMatch({required String password, required String conPassowrd}) {
    return password == conPassowrd;
  }
}
