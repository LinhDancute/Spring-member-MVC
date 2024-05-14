package com.example.springmembermvc.Controller;

import com.example.springmembermvc.Model.member;
import com.example.springmembermvc.Repository.memberRespository;
import com.example.springmembermvc.Service.memberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class memberController {

    private final memberRespository memberRepository;

    private final memberService memberService;

    public memberController(memberService memberService, memberRespository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/login_get")
    public String show_login_form(Model model)
    {
        model.addAttribute("login_request", new member());
        return "auth/login";
    }

    @GetMapping("/register_get")
    public String show_registration_form(Model model) {
        model.addAttribute("register_request", new member());
        return "auth/register";
    }

    @PostMapping("/login_post")
    public String login(@ModelAttribute("login_request") member member, Model model, RedirectAttributes redirectAttributes) {
        member authenticated = memberService.loginMember(member.getMaTV(), member.getPassword());

        if (authenticated != null) {
            model.addAttribute("login_response", authenticated);
            return "index";
        } else {
            redirectAttributes.addFlashAttribute("error", "Sai MSSV hoặc mật khẩu");
            return "redirect:/login_get";
        }
    }

    @PostMapping("/register_post")
    @ResponseBody
    public Map<String, String> register(@ModelAttribute("register_request") member member) {
        String registerMember = memberService.registerMember(member.getMaTV(), member.getHoTen(), member.getEmail(), member.getPassword());

        Map<String, String> response = new HashMap<>();
        response.put("message", registerMember);

        if ("Registration successful".equals(registerMember)) {
            response.put("redirect", "/login_get");
        }

        return response;
    }


}
