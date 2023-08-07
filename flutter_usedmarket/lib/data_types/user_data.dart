import 'dart:convert';

UserResponse userResponseFromJson(String str) => UserResponse.fromJson(json.decode(str));

String userResponseToJson(UserResponse data) => json.encode(data.toJson());

class UserResponse {
  String username;
  String name;
  DateTime createAt;
  DateTime updateAt;

  UserResponse({
    required this.username,
    required this.name,
    required this.createAt,
    required this.updateAt,
  });

  factory UserResponse.fromJson(Map<String, dynamic> json) => UserResponse(
    username: json["username"],
    name: json["name"],
    createAt: DateTime.parse(json["createAt"]),
    updateAt: DateTime.parse(json["updateAt"]),
  );

  Map<String, dynamic> toJson() => {
    "username": username,
    "name": name,
    "createAt": createAt.toIso8601String(),
    "updateAt": updateAt.toIso8601String(),
  };
}
