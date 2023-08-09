import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:used_market/data_types/constants_data.dart';
import 'package:used_market/data_types/user_data.dart';
import 'package:used_market/main.dart';
import 'package:http/http.dart' as http;

class MyPageModel extends ChangeNotifier {
  final FlutterSecureStorage _secureStorage = FlutterSecureStorage();
  String username = "";
  bool isJwtValid = false;
  Future<String> getUsername() async {
    username = (await secureStorage.read(key: 'username'))!;

    return username;
  }
  Future<void> logout() async {
    await _secureStorage.delete(key: 'jwt');
    isJwtValid = false;
    notifyListeners();
  }

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
}
