package com.example.springmembermvc.Controller;

import com.example.springmembermvc.Model.member;
import com.example.springmembermvc.Repository.member_responsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class member_controller {

    @Autowired
    private member_responsitory memberRepository;

    @GetMapping("/register")
    public String show_registration_form(Model model) {
        model.addAttribute("member", new member());
        return "register";
    }

    @PostMapping("/register")
    public String register_member(@ModelAttribute("member") member member) {
        memberRepository.save(member);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String show_login_form() {
        return "index";
    }


}
