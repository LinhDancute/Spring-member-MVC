package com.example.springmembermvc.Service.Implement;

import com.example.springmembermvc.Model.DTO.member.memberDTO;
import com.example.springmembermvc.Model.DTO.member.memberLoginDTO;
import com.example.springmembermvc.Model.DTO.member.memberRegisterDTO;
import com.example.springmembermvc.Mapper.memberMapper;
import com.example.springmembermvc.Model.Entity.memberEntity;
import com.example.springmembermvc.Repository.memberRespository;
import com.example.springmembermvc.Service.memberService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class memberServiceImpl implements memberService {

    private final memberRespository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final memberMapper memberMapper;

    public memberServiceImpl(BCryptPasswordEncoder passwordEncoder, memberRespository memberRepository, memberMapper memberMapper) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public boolean existById(int id) {
        return memberRepository.existsById(id);
    }

    @Override
    public boolean existByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public memberDTO register(memberRegisterDTO memberRegisterDTO) {
        memberEntity memberEntity = memberMapper.convertToEntity_Register(memberRegisterDTO);

        memberEntity.setId(memberRegisterDTO.getMaTV());
        memberEntity.setHoTen(memberRegisterDTO.getHoTen());
        memberEntity.setEmail(memberRegisterDTO.getEmail());
        memberEntity.setPassword(passwordEncoder.encode(memberRegisterDTO.getPassword()));

        memberEntity success =  memberRepository.save(memberEntity);
        return memberMapper.convertToDTO(success);
    }

    @Override
    public memberDTO login(memberLoginDTO memberLoginDTO) {
        memberEntity memberEntity = memberMapper.convertToEntity_Login(memberLoginDTO);
        memberEntity.setId(memberLoginDTO.getMaTV());
        memberEntity.setPassword(passwordEncoder.encode(memberLoginDTO.getPassword()));

        Optional<memberEntity> optionalMemberEntity = memberRepository.findById(memberLoginDTO.getMaTV());
        if (optionalMemberEntity.isPresent()) {
            memberEntity existingMember = optionalMemberEntity.get();
            if (passwordEncoder.matches(memberLoginDTO.getPassword(), existingMember.getPassword())) {
                return memberMapper.convertToDTO(existingMember);
            }
        }
        return null;
    }


}
