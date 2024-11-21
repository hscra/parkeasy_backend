package org.park_easy_backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.MemberDTO;
import org.park_easy_backend.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String saveForm() { return "save"; }

    @PostMapping("/save")
    public ResponseEntity<Void> save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);

        if (loginResult != null) {
            // login success
            session.setAttribute("memberEmail", loginResult.getEmail());
            session.setAttribute("memberName", loginResult.getName());

            return ResponseEntity.ok(loginResult);
        }

        // login fail
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error occurred within login procedure");
    }
}
