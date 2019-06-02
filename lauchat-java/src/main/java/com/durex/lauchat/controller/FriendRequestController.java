package com.durex.lauchat.controller;

import com.durex.lauchat.common.JsonData;
import com.durex.lauchat.param.FriendsRequestParam;
import com.durex.lauchat.param.MyFriendParam;
import com.durex.lauchat.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fr")
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;

    @PostMapping("/addFriendRequest")
    public JsonData addFriendRequest(@RequestBody MyFriendParam myFriendParam) {
        friendRequestService.sendFriendRequest(myFriendParam);
        return JsonData.success();
    }

    @GetMapping("/friendRequestList")
    public JsonData friendRequestList(String acceptUserId) {
        return JsonData.success(friendRequestService.getFriendRequestList(acceptUserId));
    }

    @GetMapping("/countNotReadyFriendRequestList")
    public JsonData countNotReadyFriendRequestList(String acceptUserId) {
        return JsonData.success(friendRequestService.getCountNotReadyFriendRequestList(acceptUserId));
    }

    @PostMapping("/operatorFriendRequest")
    public JsonData operatorFriendRequest(@RequestBody FriendsRequestParam friendsRequestParam) {
        return JsonData.success(friendRequestService.agreeFriendRequest(friendsRequestParam));
    }
}
