package com.yeonjin.security3.user.repository;

import com.yeonjin.security3.user.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByMemberId(String id);
}
