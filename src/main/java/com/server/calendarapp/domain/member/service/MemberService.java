package com.server.calendarapp.domain.member.service;

import com.server.calendarapp.domain.member.dto.MemberDto;
import com.server.calendarapp.domain.member.model.Member;
import com.server.calendarapp.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createMember(MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());

        memberRepository.createMember(member);
    }
}
