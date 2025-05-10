package com.server.calendarapp.domain.member.controller;

import com.server.calendarapp.domain.member.dto.MemberDto;
import com.server.calendarapp.domain.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public void createMember(@RequestBody MemberDto memberDto) {
        memberService.createMember(memberDto);
    }
}
