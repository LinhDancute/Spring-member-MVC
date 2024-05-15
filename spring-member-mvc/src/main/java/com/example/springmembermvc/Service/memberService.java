package com.example.springmembermvc.Service;

import com.example.springmembermvc.Model.DTO.member.memberDTO;
import com.example.springmembermvc.Model.DTO.member.memberLoginDTO;
import com.example.springmembermvc.Model.DTO.member.memberRegisterDTO;
import org.springframework.stereotype.Service;

@Service
public interface  memberService {

    boolean existById(int id);

    boolean existByEmail(String Email);

    memberDTO register(memberRegisterDTO memberRegisterDTO);
    memberDTO login (memberLoginDTO memberLoginDTO);
//    public String registerMember(int MaTV, String HoTen, String Email, String Password) {
//        if (HoTen == null || HoTen.equals("") || Email == null || Email.equals("") || Password == null) {
//            return "Chi tiết đăng ký không hợp lệ";
//        }else {
//            member email_exited = memberRespository.findByEmail(Email);
//
//            if (email_exited != null) {
//                return "Email đã tồn tại";
//            }
//
//            member member = new member();
//
//            member.setMaTV(MaTV);
//            member.setHoTen(HoTen);
//            member.setEmail(Email);
//            member.setKhoa(null);
//            member.setNganh(null);
//            member.setSDT(null);
//            member.setPassword(Password);
//            memberRespository.save(member);
//        }
//        return "Registration successful";
//    }
//    public member loginMember(int MaTV, String password) {
//        member member = memberRespository.findByMaTV(MaTV);
//        if (member != null && member.getPassword().equals(password)) {
//            return member;
//        }
//        return null;
//    }
}
