
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:used_market/data_types/constants_data.dart';
import 'package:used_market/data_types/user_data.dart';
import 'package:used_market/view_models/mypage_view_model.dart';
import 'package:used_market/views/login.dart';

class MyPage extends StatelessWidget {
  const MyPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final myPageModel = Provider.of<MyPageModel>(context);

    return myPageModel.isJwtValid
        ? MyPagee()
        : LoginPage(); // 로그인 상태에 따라 페이지 결정
  }
}

class MyPagee extends StatelessWidget {
  const MyPagee({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final myPageModel = Provider.of<MyPageModel>(context);

    return Scaffold(
      body: Center(
        child: FutureBuilder<String>(
          future: myPageModel.getUsername(),
          builder: (BuildContext context, AsyncSnapshot<String> snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return CircularProgressIndicator();
            } else if (snapshot.hasError) {
              return Text('Error: ${snapshot.error}');
            } else {
              String username = snapshot.data!;

              return FutureBuilder<UserResponse>(
                future: myPageModel.userDetail(username),
                builder: (BuildContext context, AsyncSnapshot<UserResponse> snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return CircularProgressIndicator();
                  } else if (snapshot.hasError) {
                    return Text('Error: ${snapshot.error}');
                  } else {
                    UserResponse userResponse = snapshot.data!;

                    return Padding(
                      padding: EdgeInsets.all(5),
                      child: Column(
                        children: [
                          Image.network(
                              '${Constants.api_gate_url}/user-service/pic/${userResponse.username}'),
                          Text('ID: ${userResponse.username}'),
                          Text('이름: ${userResponse.name}'),
                          Text('가입날짜: ${userResponse.createAt}'),
                          TextButton(
                            onPressed: () {
                              myPageModel.logout();
                            },
                            child: Text("로그아웃"),
                          )
                        ],
                      ),
                    );
                  }
                },
              );
            }
          },
        ),
      ),
    );
  }
}



