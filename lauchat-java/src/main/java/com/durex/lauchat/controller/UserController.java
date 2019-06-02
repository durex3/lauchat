package com.durex.lauchat.controller;

import com.durex.lauchat.common.JsonData;
import com.durex.lauchat.param.FaceParam;
import com.durex.lauchat.param.NicknameParam;
import com.durex.lauchat.param.RegisterOrLoginParam;
import com.durex.lauchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/u")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public JsonData register(@RequestBody RegisterOrLoginParam registerOrLoginParam) {
        if (!userService.userRegister(registerOrLoginParam)) {
            return JsonData.fail("注册失败");
        }
        return JsonData.success();
    }

    @PostMapping("/login")
    public JsonData login(@RequestBody RegisterOrLoginParam registerOrLoginParam) {
        return JsonData.success(userService.queryUserFormLogin(registerOrLoginParam));
    }

    @PostMapping("/uploadFaceBase64")
    public JsonData uploadFaceBase64(@RequestBody FaceParam faceParam) {
        return JsonData.success(userService.updateUserFace(faceParam));
    }

    @PostMapping("/setNickname")
    public JsonData setNickname(@RequestBody NicknameParam nicknameParam) {
        return JsonData.success(userService.updateUserNickname(nicknameParam));
    }
}
