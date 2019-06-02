package com.durex.lauchat.controller;

import com.durex.lauchat.common.JsonData;
import com.durex.lauchat.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ug")
@RestController
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @GetMapping("/getUserGroupList")
    public JsonData getUserGroupByUserId(@RequestParam("userId") String userId) {
        return JsonData.success(userGroupService.getUserGroupByUserId(userId));
    }
}
