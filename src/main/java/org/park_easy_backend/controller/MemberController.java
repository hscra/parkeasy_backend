package org.park_easy_backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.MemberDTO;
import org.park_easy_backend.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MemberDTO memberDTO) {
        try {
            memberService.save(memberDTO);
        }  catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Duplicate entry: The user already exists.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred within user save procedure");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO, HttpSession session) {
        // Check if user logged in already
        if (session.getAttribute("member") != null) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        // Login
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // Login success
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
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
