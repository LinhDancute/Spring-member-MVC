package com.example.springmembermvc.Controller;

import com.example.springmembermvc.Mapper.memberMapper;
import com.example.springmembermvc.Model.DTO.member.memberRegisterDTO;
import com.example.springmembermvc.Model.DTO.member.memberDTO;
import com.example.springmembermvc.Repository.memberRespository;
import com.example.springmembermvc.Service.memberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class memberController {

    private final memberRespository memberRepository;

    private final memberService memberService;
    private final memberMapper memberMapper;

    public memberController(memberService memberService, memberRespository memberRepository, memberMapper memberMapper) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @GetMapping("/login_get")
    public String show_login_form(Model model)
    {
        model.addAttribute("login_request", new memberDTO());
        return "auth/login";
    }

    @GetMapping("/register_get")
    public String show_registration_form(Model model) {
        model.addAttribute("register_request", new memberDTO());
        return "auth/register";
    }

//    @PostMapping("/login_post")
//    public String login(@ModelAttribute("login_request") member member, Model model, RedirectAttributes redirectAttributes) {
//        member authenticated = memberService.loginMember(member.getMaTV(), member.getPassword());
//
//        if (authenticated != null) {
//            model.addAttribute("login_response", authenticated);
//            return "index";
//        } else {
//            redirectAttributes.addFlashAttribute("error", "Sai MSSV hoặc mật khẩu");
//            return "redirect:/login_get";
//        }
//    }

    @PostMapping("/register_post")
    @ResponseBody
    public Map<String, String> register(
            @Valid @ModelAttribute("register_request") memberRegisterDTO register,
            BindingResult result
    ) {
        Map<String, String> response = new HashMap<>();

        if (result.hasErrors()) {
            response.put("message", "Đã xảy ra lỗi xác thực");
            return response;
        }

        if (memberService.existById(register.getMaTV())) {
            response.put("message", "MSSV đã tồn tại");
            return response;
        } else if (memberService.existByEmail(register.getEmail())) {
            response.put("message", "Email đã tồn tại");
            return response;
        }

        memberService.register(register);

        response.put("message", "Đăng ký thành công");
        response.put("redirect", "/login_get");
        return response;
    }



}
