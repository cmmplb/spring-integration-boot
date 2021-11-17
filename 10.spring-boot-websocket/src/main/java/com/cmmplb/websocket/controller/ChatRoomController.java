package com.cmmplb.websocket.controller;

import com.cmmplb.websocket.config.properties.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author penglibo
 * @date 2021-05-29 09:48:26
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/chat/room")
public class ChatRoomController {

    @Autowired
    private ServerProperties serverProperties;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("public/chatRoom.html");
        modelAndView.addObject("port", serverProperties.getPort());
        modelAndView.addObject("contextPath", serverProperties.getContextPath());
        return modelAndView;
    }


}
