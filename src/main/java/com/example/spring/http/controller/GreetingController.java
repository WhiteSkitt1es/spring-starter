package com.example.spring.http.controller;

import com.example.spring.database.entity.Role;
import com.example.spring.dto.UserReadDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@SessionAttributes({"user"})
public class GreetingController {

    @ModelAttribute("roles")
    public List<Role> roles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping(value = "/hello")
    public String hello(Model model, HttpServletRequest request, @ModelAttribute UserReadDto userReadDto) {
//        request.getSession().setAttribute(); sessionScope
//        request.setAttribute(); requestScope
//        request.getSession().getAttribute("user");
//        model.addAttribute("user", new UserReadDto(1L, "Ivan"));
        return "greeting/hello";
    }

    //    @RequestMapping(value = "/bye", method = RequestMethod.GET)
    @GetMapping("/bye")
    public String bye(@SessionAttribute("user") UserReadDto user, Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("greeting/bye");
        return "greeting/bye";
    }

    //    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @GetMapping(value = "/hello/{id}")
    public String hello2(ModelAndView modelAndView,
                         HttpServletRequest request,
                         @RequestParam Integer age,
                         @RequestHeader String accept,
                         @CookieValue("JSESSIONID") String jsessionId,
                         @PathVariable Integer id) {
        String requestParameter = request.getParameter("age");
        String acceptHeader = request.getHeader("accept");
        Cookie[] cookies = request.getCookies();
        modelAndView.setViewName("greeting/hello");
        return "greeting/hello";
    }
}
