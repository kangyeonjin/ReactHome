package com.yeonjin.security2.member.repository;

import com.yeonjin.security2.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public  interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findMemberByMemberId(String memberId);
}
