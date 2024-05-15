package com.example.springmembermvc.Repository;

import com.example.springmembermvc.Model.member;
import com.example.springmembermvc.Model.usage_information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface usage_information_repository extends JpaRepository<usage_information, Integer> {
}
