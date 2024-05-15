package com.example.springmembermvc.Controller;

import com.example.springmembermvc.Mapper.memberMapper;
import com.example.springmembermvc.Model.DTO.member.memberLoginDTO;
import com.example.springmembermvc.Model.DTO.member.memberRegisterDTO;
import com.example.springmembermvc.Model.DTO.member.memberDTO;
import com.example.springmembermvc.Repository.memberRespository;
import com.example.springmembermvc.Service.memberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class memberController {

    private final memberRespository memberRepository;

    public final memberService memberService;
    private final memberMapper memberMapper;

    public memberController(memberService memberService, memberRespository memberRepository, memberMapper memberMapper) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @GetMapping("/admin")
    public String go_to_admin_page() {
        return "admin/index";
    }

    @GetMapping("/login_get")
    public String show_login_form(Model model)
    {
        model.addAttribute("login_request", new memberDTO());
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    @GetMapping("/register_get")
    public String show_registration_form(Model model) {
        model.addAttribute("register_request", new memberDTO());
        return "auth/register";
    }

    @PostMapping("/login_post")
    public String login(@ModelAttribute("login_request") memberLoginDTO login,
                        BindingResult result,
                        HttpSession session,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "redirect:/login_get";
        }

        memberDTO loggedInMember = memberService.login(login);

        if (loggedInMember != null) {
            session.setAttribute("login_response", loggedInMember);
            model.addAttribute("login_response_info", loggedInMember);
            redirectAttributes.addAttribute("successLogin", "true");
            redirectAttributes.addFlashAttribute("login", login);
            return "redirect:/index";
        } else {
            redirectAttributes.addFlashAttribute("loginError", "true");
            return "redirect:/login_get";
        }
    }

    @PostMapping("/register_post")
    public String register(
            @Valid @ModelAttribute("register_request") memberRegisterDTO register,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "redirect:/register_get";
        }else if (memberService.existById(register.getMaTV())) {
            redirectAttributes.addAttribute("existedMSSV", "true");
            redirectAttributes.addFlashAttribute("register", register);
            return "redirect:/register_get";
        } else if (memberService.existByEmail(register.getEmail())) {
            redirectAttributes.addAttribute("existedEmail", "true");
            redirectAttributes.addFlashAttribute("register", register);
            return "redirect:/register_get";
        }

        memberService.register(register);
        redirectAttributes.addAttribute("successRegister", "true");
        redirectAttributes.addFlashAttribute("register", register);
        return "redirect:/login_get";
    }
}
