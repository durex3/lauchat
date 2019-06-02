package com.durex.lauchat.controller;

import com.durex.lauchat.common.JsonData;
import com.durex.lauchat.param.MyFriendParam;
import com.durex.lauchat.service.MyFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/f")
public class MyFriendController {

    @Autowired
    private MyFriendService myFriendService;


    @GetMapping("/search")
    public JsonData search(MyFriendParam myFriendParam) {
        return JsonData.success(myFriendService.preconditionSearchFriend(myFriendParam));
    }

    @GetMapping("/myFriends")
    public JsonData myFriends(@RequestParam String userId) {
        return JsonData.success(myFriendService.getMyFriendList(userId));
    }
}
