package com.vttu.kpis.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class SessionController {


    @PostMapping("/setCookie")
    public String  saveToSession(HttpServletResponse response, @RequestBody String jsonString) {



        Cookie cookie = new Cookie("user", jsonString);
        cookie.setMaxAge(3600); // Thời gian sống của cookie trong giây (ở đây là 1 giờ)
        response.addCookie(cookie);
        return "Cookie đã được tạo và gửi đi.";
    }

    @GetMapping("/getCookie")
    public String getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userInfo")) {
                    return "Cookie value: " + cookie.getValue();
                }
            }
        }
        return "Không tìm thấy cookie có tên là 'user'.";
    }
}
