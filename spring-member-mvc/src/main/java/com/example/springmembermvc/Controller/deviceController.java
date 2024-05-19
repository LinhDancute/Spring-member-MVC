package com.example.springmembermvc.Controller;

import com.example.springmembermvc.Mapper.deviceMapper;
import com.example.springmembermvc.Mapper.memberMapper;
import com.example.springmembermvc.Model.DTO.member.memberDTO;
import com.example.springmembermvc.Model.Entity.deviceEntity;
import com.example.springmembermvc.Model.Entity.memberEntity;
import com.example.springmembermvc.Model.Entity.usage_informationEntity;
import com.example.springmembermvc.Repository.memberRespository;
import com.example.springmembermvc.Repository.usage_informationRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.springmembermvc.Repository.deviceRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

@Controller
public class deviceController {
    private final deviceRepository deviceRepository;

    private final memberRespository memberRespository;

    private final usage_informationRepository usage_information_repository;
    private final com.example.springmembermvc.Mapper.memberMapper memberMapper;
    private final com.example.springmembermvc.Mapper.deviceMapper deviceMapper;

    public deviceController(deviceRepository deviceRepository, memberRespository memberRespository, usage_informationRepository usage_information_repository, memberMapper memberMapper, deviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.memberRespository = memberRespository;
        this.usage_information_repository = usage_information_repository;
        this.memberMapper = memberMapper;
        this.deviceMapper = deviceMapper;
    }

    // Khởi tạo mảng Cart
    private List<deviceEntity> cart = new ArrayList<>();


    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "0") int page) {
        // Tạo đối tượng Pageable để lấy dữ liệu theo trang
        Pageable pageable = PageRequest.of(page, 12);
        // Lấy danh sách thiết bị từ cơ sở dữ liệu
        Page<deviceEntity> devicePage = deviceRepository.findAll(pageable);
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

     //add device to cart
    @GetMapping("/addToCart/{MaTB}")
    public String addToCart(@PathVariable int MaTB, HttpSession session) {
        // get user information
        memberDTO loggedInMember = (memberDTO) session.getAttribute("login_response");
        if (loggedInMember == null) {
            // If the user is not logged in, redirect to login page
            return "redirect:/login_get";
        }

        // get device by id
        Optional<deviceEntity> optionalDevice = deviceRepository.findById(MaTB);
        if (optionalDevice.isPresent()) {
            deviceEntity selectedDevice = optionalDevice.get();

            // checking device exist in cart
            boolean found = false;
            for (deviceEntity item : cart) {
                if (item.getId().equals(selectedDevice.getId())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                cart.add(selectedDevice);
                selectedDevice.setTrangThai(2);

                // add information of device to usage_information
                usage_informationEntity usage_information = new usage_informationEntity();
                usage_information.setId(selectedDevice.getId());
                usage_information.setMaTV(memberMapper.toEntity(loggedInMember));
                usage_information.setMaTB(selectedDevice);
                usage_information.setTGDatcho(Instant.now());

                usage_information = usage_information_repository.save(usage_information);
                selectedDevice.getThongtinsds().add(usage_information);
                deviceRepository.save(selectedDevice);
            }
        }

        // move to home page
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
    public List<deviceEntity> getCart() {
        return cart;
    }

    @PostMapping("/confirm")
    @ResponseBody
    public Map<String, String> confirm(@RequestParam String name, @RequestParam String MSSV, @PathVariable int MaTB) {
        Map<String, String> response = new HashMap<>();
        try {
            int maTV = Integer.parseInt(MSSV);
            Optional<memberEntity> optionalMember = memberRespository.findById(maTV);
            Optional<deviceEntity> device = deviceRepository.findById(MaTB);
            System.out.println(device);
            if (optionalMember.isPresent()) {
                memberEntity foundMember = optionalMember.get();
                for (deviceEntity d : cart) {
                    usage_informationEntity info = new usage_informationEntity();
                    info.setId(1);
                    info.setMaTV(foundMember);
                    info.setTGVao(Instant.now());
                    // Lưu thông tin vào cơ sở dữ liệu
                    usage_information_repository.save(info);
                }
                response.put("message", "Lưu thông tin thành công");
                cart.clear();
            } else {
                response.put("message", "MSSV không tồn tại");
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping("/preOrder")
    @ResponseBody
    public Map<String, Object> handlePreOrder(
            @RequestParam("productId") int productId,
            @RequestParam("studentID") String studentID,
            @RequestParam(value = "preorderDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate preorderDate) {

        Map<String, Object> response = new HashMap<>();
        try {
            Optional<memberEntity> optionalMember = memberRespository.findById(Integer.parseInt(studentID));
            Optional<deviceEntity> device = deviceRepository.findById(productId);

            if (optionalMember.isPresent() && device.isPresent()) {
                memberEntity user = optionalMember.get();
                deviceEntity selectedDevice = device.get();

                //set usage information
                usage_informationEntity usageInformation = new usage_informationEntity();
                usageInformation.setMaTV(user);
                usageInformation.setMaTB(selectedDevice);
                usageInformation.setTGDatcho(preorderDate.atStartOfDay().toInstant(ZoneOffset.UTC));

                //set device status
                selectedDevice.setTrangThai(1);

                // Save the usage information
                usageInformation = usage_information_repository.save(usageInformation);
                selectedDevice.getThongtinsds().add(usageInformation);
                deviceRepository.save(selectedDevice);

                response.put("message", "Pre-order successful");
            } else {
                response.put("message", "Member or Device not found");
            }
        } catch (Exception e) {
            response.put("message", "Failed to pre-order: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }


    // API endpoint to check if MSSV exists
    @GetMapping("/api/checkMSSV")
    @ResponseBody
    public Map<String, Boolean> checkMSSV(@RequestParam int MSSV) {
        Optional<memberEntity> optionalMember = memberRespository.findById(MSSV);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", optionalMember.isPresent());
        return response;
    }

    @GetMapping("/category/{category}")
    public String viewByCategory(@PathVariable int category, Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 12);
        Page<deviceEntity> devicePage;

        switch (category) {
            case 1:
                devicePage = deviceRepository.findByMaTBStartingWith(1, pageable);
                break;
            case 2:
                devicePage = deviceRepository.findByMaTBStartingWith(2, pageable);
                break;
            case 3:
                devicePage = deviceRepository.findByMaTBStartingWith(3, pageable);
                break;
            case 4:
                devicePage = deviceRepository.findByMaTBStartingWith(4, pageable);
                break;
            case 5:
                devicePage = deviceRepository.findByMaTBStartingWith(5, pageable);
                break;
            case 6:
                devicePage = deviceRepository.findByMaTBStartingWith(6, pageable);
                break;
            default:
                devicePage = Page.empty();
                break;
        }

        model.addAttribute("devices", devicePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", devicePage.getTotalPages());
        model.addAttribute("category", category);

        return "index";
    }

    // Method to handle search requests
    @GetMapping("/search")
    public String searchDevices(@RequestParam("query") String query, Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 12);
        Page<deviceEntity> devicePage = deviceRepository.findByMaTBContaining(query, pageable);

        model.addAttribute("devices", devicePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", devicePage.getTotalPages());
        model.addAttribute("searchQuery", query);

        return "index";
    }


}

