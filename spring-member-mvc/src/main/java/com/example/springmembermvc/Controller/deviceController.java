package com.example.springmembermvc.Controller;

import com.example.springmembermvc.Mapper.deviceMapper;
import com.example.springmembermvc.Mapper.memberMapper;
import com.example.springmembermvc.Model.DTO.member.memberDTO;
import com.example.springmembermvc.Model.Entity.deviceEntity;
import com.example.springmembermvc.Model.Entity.memberEntity;
import com.example.springmembermvc.Model.Entity.usage_informationEntity;
import com.example.springmembermvc.Repository.memberRespository;
import com.example.springmembermvc.Repository.usage_informationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.springmembermvc.Service.deviceService;

import com.example.springmembermvc.Repository.deviceRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;

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

    @GetMapping("/borrow/{deviceId}")
    public ResponseEntity<?> addToCart(@PathVariable("deviceId") int deviceId, HttpSession session) {
        memberDTO loggedInMember = (memberDTO) session.getAttribute("login_response");
        if (loggedInMember == null) {
            // If the user is not logged in, return an unauthorized response
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Optional<deviceEntity> optionalDevice = deviceRepository.findById(deviceId);

        if (optionalDevice.isPresent()) {
            deviceEntity selectedDevice = optionalDevice.get();

            // Get reservation time of the device
            Optional<usage_informationEntity> usage_information = usage_information_repository.findById(selectedDevice.getId());
            if (usage_information.isPresent()) {
                usage_informationEntity usage_information1 = usage_information.get();
                Instant reservationTime = usage_information1.getTGDatcho();

                System.out.println(reservationTime);
                // Get the current time
                Instant currentTime = Instant.now();

                // Compare reservation time with the current time
                if (reservationTime != null) {
                    Duration duration = Duration.between(reservationTime, currentTime);
                    if (duration.toHours() > 1) {
                        selectedDevice.setTrangThai(0);
                    }
                }
                System.out.println("ok");
            }

            // Update status for the device
            switch (selectedDevice.getTrangThai()) {
                case 1:
                    Map<String, Object> borrowingResponse = new HashMap<>();
                    borrowingResponse.put("success", false);
                    borrowingResponse.put("message", "Device is currently being borrowed");
                    return ResponseEntity.ok(borrowingResponse);
                case 2:
                    Map<String, Object> preOrderResponse = new HashMap<>();
                    preOrderResponse.put("success", false);
                    preOrderResponse.put("message", "Device has been pre-ordered");
                    return ResponseEntity.ok(preOrderResponse);
                default:
                    usage_informationEntity usageInformation = new usage_informationEntity();
                    usageInformation.setMaTV(memberMapper.toEntity(loggedInMember));
                    usageInformation.setMaTB(selectedDevice);
                    usageInformation.setTGDatcho(Instant.now());

                    // Set device status
                    selectedDevice.setTrangThai(1);

                    usageInformation = usage_information_repository.save(usageInformation);
                    selectedDevice.getThongtinsds().add(usageInformation);
                    deviceRepository.save(selectedDevice);

                    Map<String, Object> successResponse = new HashMap<>();
                    successResponse.put("success", true);
                    successResponse.put("message", "Borrowed successfully");
                    return ResponseEntity.ok(successResponse);
            }
        } else {
            Map<String, Object> notFoundResponse = new HashMap<>();
            notFoundResponse.put("success", false);
            notFoundResponse.put("message", "Device not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
        }
    }


    @CrossOrigin(origins = "http://localhost:8080")
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
                switch (selectedDevice.getTrangThai()) {
                    case 1:
                        response.put("message","Device have been borrowed");
                    case 2:
                        response.put("message","Device have been pre-order");
                    default:
                        usage_informationEntity usageInformation = new usage_informationEntity();
                        usageInformation.setMaTV(user);
                        usageInformation.setMaTB(selectedDevice);
                        usageInformation.setTGDatcho(preorderDate.atStartOfDay().toInstant(ZoneOffset.UTC));

                        //set device status
                        selectedDevice.setTrangThai(2);

                        // Save the usage information
                        usageInformation = usage_information_repository.save(usageInformation);
                        selectedDevice.getThongtinsds().add(usageInformation);
                        deviceRepository.save(selectedDevice);

                        response.put("message", "Pre-order successful");
                }
//                if (selectedDevice.getTrangThai() == 1) {
//                    response.put("message", "san pham dang duoc muon");
//                }
            } else {
                response.put("message", "Member or Device not found");
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


}

