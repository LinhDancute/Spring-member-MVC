package com.example.springmembermvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminController {

    @GetMapping("/menu")
    public String go_to_menu_page() {
        return "admin/menu";
    }

    @GetMapping("/edit-menu")
    public String go_to_edit_menu_page() {
        return "admin/edit-menu";
    }

    @GetMapping("/edit-banner")
    public String go_to_edit_banner_page() {
        return "admin/edit-banner";
    }

    @GetMapping("/blank")
    public String go_to_blank_page() {
        return "admin/blank";
    }

    @GetMapping("/banner")
    public String go_to_banner_page() {
        return "admin/banner";
    }

    @GetMapping("/add-menu")
    public String go_to_add_menu_page() {
        return "admin/add-menu";
    }

    @GetMapping("/add-banner")
    public String go_to_add_banner_page() {
        return "admin/add-banner";
    }
}
