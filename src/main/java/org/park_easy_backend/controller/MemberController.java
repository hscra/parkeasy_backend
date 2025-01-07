package org.park_easy_backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.MemberDTO;
import org.park_easy_backend.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@ModelAttribute MemberDTO memberDTO) {
        try {
            memberService.save(memberDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred within login procedure");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO, HttpSession session) {
        // Check if user logged in already
        if (session.getAttribute("member") != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User already logged in!");
        }

        // Login
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login success
            session.setAttribute("member", loginResult);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        // Response on fail
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error occurred within login procedure");

    }

    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        Optional<MemberDTO> member = Optional.ofNullable((MemberDTO) session.getAttribute("member"));

        if (member.isPresent()) {
            return ResponseEntity.ok(member);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("User already logged in");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
