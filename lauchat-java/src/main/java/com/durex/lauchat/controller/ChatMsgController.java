package com.durex.lauchat.controller;

import com.durex.lauchat.common.FastDFSClient;
import com.durex.lauchat.common.JsonData;
import com.durex.lauchat.service.ChatMsgService;
import com.durex.lauchat.service.UserGroupMsgService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@RestController
public class ChatMsgController {

    @Autowired
    FastDFSClient fastDFSClient;
    @Autowired
    ChatMsgService chatMsgService;
    @Autowired
    UserGroupMsgService userGroupMsgService;

    @PostMapping("/upload")
    public JsonData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        try {
            return JsonData.success(fastDFSClient.uploadFile(file));
        } catch (IOException e) {
            e.printStackTrace();
            return JsonData.fail("fail");
        }
    }

    @GetMapping("/getUnReadMsgList")
    public JsonData getUnReadMsgList(@RequestParam(value = "acceptUserId", required = true) String acceptUserId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("unReadMsgList", chatMsgService.getUnReadMsgList(acceptUserId));
        map.put("unReadGroupMsgList", userGroupMsgService.getUnReadMsgList(acceptUserId));
        return JsonData.success(map);
    }

}
