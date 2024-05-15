package com.example.springmembermvc.Controller;

import com.example.springmembermvc.Model.member;
import com.example.springmembermvc.Model.usage_information;
import com.example.springmembermvc.Repository.member_responsitory;
import com.example.springmembermvc.Repository.usage_information_repository;
import org.aspectj.weaver.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.springmembermvc.Repository.device_repository;
import com.example.springmembermvc.Model.device;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class device_controller {
    @Autowired
    private device_repository device_repository;

    @Autowired
    private member_responsitory member_responsitory;

    @Autowired
    private usage_information_repository usage_information_repository;

    // Khởi tạo mảng Cart
    private List<device> cart = new ArrayList<>();

    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "0") int page) {
        // Tạo đối tượng Pageable để lấy dữ liệu theo trang
        Pageable pageable = PageRequest.of(page, 12);
        // Lấy danh sách thiết bị từ cơ sở dữ liệu
        Page<device> devicePage = device_repository.findAll(pageable);
        // Thêm danh sách thiết bị vào model để truyền cho view
        model.addAttribute("devices", devicePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", devicePage.getTotalPages());
        return "index";
    }

    @GetMapping("/index") // Thêm phương thức xử lý yêu cầu cho "/index"
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        return home(model, page); // Chuyển hướng yêu cầu đến phương thức home
    }

    // Xử lý thêm thiết bị vào giỏ hàng
    @GetMapping("/addToCart/{MaTB}")
    public String addToCart(@PathVariable String MaTB) {
        // Lấy thiết bị từ cơ sở dữ liệu dựa vào deviceId
        Optional<device> optionalDevice = device_repository.findById(MaTB);
        if (optionalDevice.isPresent()) {
            device selectedDevice = optionalDevice.get();
            // Kiểm tra xem thiết bị đã tồn tại trong giỏ hàng chưa
            boolean found = false;
            for (device item : cart) {
                if (item.getMaTB().equals(selectedDevice.getMaTB())) {
                    // Nếu thiết bị đã tồn tại trong giỏ hàng, tăng số lượng lên 1
                    found = true;
                    break;
                }
            }
            // Nếu thiết bị chưa tồn tại trong giỏ hàng, thêm vào giỏ hàng và đặt số lượng là 1
            if (!found) {
                cart.add(selectedDevice);
            }
        }
        // Chuyển hướng người dùng đến trang chính
        return "redirect:/";
    }

    // Endpoint để trả về trang giỏ hàng
    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cart);
        return "cart";
    }

    // Getter cho Cart
    @ModelAttribute("cart")
    public List<device> getCart() {
        return cart;
    }


    @PostMapping("/confirm")
    public String confirm(@RequestParam String name, @RequestParam String MSSV, Model model) {
        try {
            int maTV = Integer.parseInt(MSSV);
            Optional<member> optionalMember = member_responsitory.findById(maTV);
            if (optionalMember.isPresent()) {
                member foundMember = optionalMember.get();
                for (device d : cart) {
                    usage_information info = new usage_information();
                    info.setThanhvien(foundMember);
                    info.setThietbi(d);
                    info.setTGVao(LocalDateTime.now());
                    usage_information_repository.save(info);
                }
                model.addAttribute("message", "Lưu thông tin thành công");
                cart.clear();
            } else {
                model.addAttribute("message", "MSSV không tồn tại");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/";
    }



    // API endpoint to check if MSSV exists
    @GetMapping("/api/checkMSSV")
    @ResponseBody
    public Map<String, Boolean> checkMSSV(@RequestParam String MSSV) {
        int maTV = Integer.parseInt(MSSV);
        Optional<member> optionalMember = member_responsitory.findById(maTV);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", optionalMember.isPresent());
        return response;
    }

}

