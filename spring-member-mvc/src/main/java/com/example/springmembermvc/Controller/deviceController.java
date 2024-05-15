package com.example.springmembermvc.Controller;

import com.example.springmembermvc.Model.DTO.device.deviceDTO;
import com.example.springmembermvc.Model.Entity.deviceEntity;
import com.example.springmembermvc.Model.Entity.memberEntity;
import com.example.springmembermvc.Model.Entity.usage_informationEntity;
import com.example.springmembermvc.Repository.memberRespository;
import com.example.springmembermvc.Repository.usage_informationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.springmembermvc.Service.deviceService;

import com.example.springmembermvc.Repository.deviceRepository;

import java.time.Instant;
import java.util.*;

@Controller
public class deviceController {
    private final deviceRepository deviceRepository;

    private final memberRespository memberRespository;

    private final usage_informationRepository usage_information_repository;

    public deviceController(deviceRepository deviceRepository, memberRespository memberRespository, usage_informationRepository usage_information_repository) {
        this.deviceRepository = deviceRepository;
        this.memberRespository = memberRespository;
        this.usage_information_repository = usage_information_repository;
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

    // Xử lý thêm thiết bị vào giỏ hàng
    @GetMapping("/addToCart/{MaTB}")
    public String addToCart(@PathVariable int MaTB) {
        // Lấy thiết bị từ cơ sở dữ liệu dựa vào deviceId
        Optional<deviceEntity> optionalDevice = deviceRepository.findById(MaTB);
        if (optionalDevice.isPresent()) {
            deviceEntity selectedDevice = optionalDevice.get();
            // Kiểm tra xem thiết bị đã tồn tại trong giỏ hàng chưa
            boolean found = false;
            for (deviceEntity item : cart) {
                if (item.getId().equals(selectedDevice.getId())) {
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
    public List<deviceEntity> getCart() {
        return cart;
    }


//    @PostMapping("/confirm")
//    public String confirm(@RequestParam String name, @RequestParam String MSSV, Model model) {
//        try {
//            int maTV = Integer.parseInt(MSSV);
//            Optional<member> optionalMember = member_responsitory.findById(maTV);
//            if (optionalMember.isPresent()) {
//                member foundMember = optionalMember.get();
//                for (device d : cart) {
//                    usage_information info = new usage_information();
//                    info.setThanhvien(foundMember);
//                    info.setThietbi(d);
//                    info.setTGVao(LocalDateTime.now());
//                    usage_information_repository.save(info);
//                }
//                model.addAttribute("message", "Lưu thông tin thành công");
//                cart.clear();
//            } else {
//                model.addAttribute("message", "MSSV không tồn tại");
//            }
//        } catch (Exception e) {
//            model.addAttribute("message", "Đã xảy ra lỗi: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return "redirect:/";
//    }

//    @PostMapping("/confirm")
//    public String confirm(@RequestParam String name, @RequestParam String MSSV, Model model) {
//        try {
//            int maTV = Integer.parseInt(MSSV);
//            Optional<member> optionalMember = member_responsitory.findById(maTV);
//            if (optionalMember.isPresent()) {
//                member foundMember = optionalMember.get();
//                for (device d : cart) {
//                    usage_information info = new usage_information();
//                    info.setThanhvien(foundMember);
//                    info.setThietbi(d);
//                    info.setTGVao(LocalDateTime.now());
//                    usage_information_repository.save(info);
//                }
//                model.addAttribute("message", "Lưu thông tin thành công");
//                cart.clear();
//            } else {
//                model.addAttribute("message", "MSSV không tồn tại");
//            }
//        } catch (Exception e) {
//            model.addAttribute("message", "Đã xảy ra lỗi: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return "redirect:/";
//    }

    @PostMapping("/confirm")
    @ResponseBody
    public Map<String, String> confirm(@RequestParam String name, @RequestParam String MSSV, Model model) {
        Map<String, String> response = new HashMap<>();
        try {
            int maTV = Integer.parseInt(MSSV);
            Optional<memberEntity> optionalMember = memberRespository.findById(maTV);
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

    @Autowired
    private deviceService deviceService;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<deviceDTO> devices = deviceService.getAllDevices();
        model.addAttribute("devices", devices);
        return "admin/menu";
    }

    // Endpoint để hiển thị trang thêm mới thiết bị
    @GetMapping("/add-menu")
    public String showAddDeviceForm(Model model) {
        model.addAttribute("device", new deviceDTO());
        return "admin/add-menu"; // Đảm bảo bạn đã tạo trang HTML này
    }

    // Endpoint để xử lý yêu cầu thêm mới thiết bị
    @PostMapping("/add-menu")
    public String addDevice(@ModelAttribute("device") deviceDTO device) {
        // Thêm mới thiết bị vào cơ sở dữ liệu bằng cách sử dụng service
        deviceService.saveDevice(device);
        // Sau khi thêm mới, chuyển hướng người dùng đến trang danh sách thiết bị hoặc trang cần thiết khác
        return "redirect:/menu"; // Điều hướng đến trang danh sách thiết bị
//        return "admin/menu";
    }


}

