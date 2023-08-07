import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:used_market/data_types/constants_data.dart';
import 'package:http/http.dart' as http;
import 'package:used_market/data_types/user_data.dart';
import 'package:used_market/main.dart';

class MyPage extends StatelessWidget {
  const MyPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    return MyPagee();
  }
}

class MyPagee extends StatelessWidget {
  const MyPagee({Key? key}) : super(key: key);


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
      child: FutureBuilder<UserResponse>(
        future: userDetail(_username),
        builder:
        (BuildContext context, AsyncSnapshot<UserResponse> snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return CircularProgressIndicator();
          }else if(snapshot.hasError){
            return Text('Error: ${snapshot.error}');
          }else {
            UserResponse userResponse = snapshot.data!;

            return Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Image.network(userPic),
                Text(userPic),
                Text('ID: ${userResponse.username}'),
                Text('이름: ${userResponse.name}'),
                Text('가입날짜: ${userResponse.createAt}'),
              ],
            );

          }

        }
      ),
      ),
    );
  }
}
final FlutterSecureStorage _secureStorage = FlutterSecureStorage();
String _username = "";
Future<String> getUsername() async {
  _username = (await secureStorage.read(key: 'username'))!;

  return _username;
}
String userPic = '${Constants.api_gate_url}/user-service/pic/${_username}';



Future<UserResponse> userDetail(String username) async {

  String? jwtToken = await _secureStorage.read(key: 'jwt');
  String url = '${Constants.api_gate_url}/user-service/users/${username}';
  final response = await http.get(
    Uri.parse(url),
    headers: {
      "Content-Type" : "application/json",
      "Authorization" : "Bearer $jwtToken",
    },
  );

  print('Response status code: ${response.statusCode}');
  print('Response body: ${response.body}');

  if (response.statusCode == 200) {
    List<dynamic> responseData = jsonDecode(response.body);
    if(responseData.isNotEmpty) {
    UserResponse userResponse =
    UserResponse.fromJson(responseData[0]);
    username = userResponse.username;

    return userResponse;
    } else {
      throw Exception('유저 데이터가 없습니다');
    }
  } else {
    throw Exception('Failed to load user details: ${response.statusCode}');
  }

}



