package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    /* 회원 가입 시 중복 회원 검증 */
    //jpa 모든 변경은 모두 트렌젝션 안에서 실행되어야한다. import는 spring에서 제공해주는 것 사용하기.
    @Transactional
    public Long join(Member member){
        validationDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); // 데이터베이스에서는 pk 즉 id 값이 key 값이다.
    }

    /* 전체 회원 조회 메서드 */
    @Transactional(readOnly = true)
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    /* 한명 회원 조회하기 */
    @Transactional(readOnly = true)
    public Member findOneMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }


    // 중복 회원 검증 메서드
    @Transactional(readOnly = true)
    public void validationDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findMemberByUsername(member.getUsername());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다. 다른 아이디를 입력해주시길 바랍니다.");
        }
    }

}
