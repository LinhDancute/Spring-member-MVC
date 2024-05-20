package com.example.springmembermvc.Controller;

import com.example.springmembermvc.Mapper.memberMapper;
import com.example.springmembermvc.Model.DTO.member.*;
import com.example.springmembermvc.Repository.*;
import com.example.springmembermvc.Service.*;
import com.example.springmembermvc.Model.DTO.member.*;
import com.example.springmembermvc.Model.Entity.*;
import com.example.springmembermvc.Repository.memberRespository;
import com.example.springmembermvc.Service.memberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class memberController {

    private final memberRespository memberRepository;

    public final memberService memberService;
    public final memberMapper memberMapper;
    public final deviceRepository deviceRespository;
    public final deviceService deviceService;
    public final usage_informationRepository usage_informationRepository;

    public memberController(memberService memberService, memberRespository memberRepository, memberMapper memberMapper, deviceRepository deviceRespository, com.example.springmembermvc.Service.deviceService deviceService, com.example.springmembermvc.Repository.usage_informationRepository usageInformationRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.deviceRespository = deviceRespository;
        this.deviceService = deviceService;
        usage_informationRepository = usageInformationRepository;
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

    private List<memberEntity> department = new ArrayList<>();

    @ModelAttribute("department")
    public List<memberEntity> getDepartment() {
        return department;
    }

    //LOGIN
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
        }else if (memberService.existById(register.getMaTV())) {            //check MSSV existed
            redirectAttributes.addAttribute("existedMSSV", "true");
            redirectAttributes.addFlashAttribute("register", register);
            return "redirect:/register_get";
        } else if (memberService.existByEmail(register.getEmail())) {       //check email existed
            redirectAttributes.addAttribute("existedEmail", "true");
            redirectAttributes.addFlashAttribute("register", register);
            return "redirect:/register_get";
        }

        //login success
        memberService.register(register);
        redirectAttributes.addAttribute("successRegister", "true");
        redirectAttributes.addFlashAttribute("register", register);
        return "redirect:/login_get";
    }

    //UPDATE
    @PostMapping("/edit-profile/{id}")
    public String updateProfile(
            @PathVariable("id") Integer id,
            @Valid @ModelAttribute memberUpdateDTO updateMember,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "redirect:/index";
        }

        updateMember.setId(id);

        memberService.update(updateMember);
        redirectAttributes.addAttribute("successUpdateMember", "true");
        redirectAttributes.addFlashAttribute("update", updateMember);
        return "redirect:/index";
    }
}
