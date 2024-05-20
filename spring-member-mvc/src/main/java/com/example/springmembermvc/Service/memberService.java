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
}
