package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        //이렇게 member을 모두 리턴해버리면 엔티티가 외부에 노출이 되게 되고 필요 없는 값까지 모두 가져오게 된다. 어레이로 가져오기때문에 확장성도 떨어진다.
        // 그렇다고 엔티티에 jsonIgnore 어노테이션을 쓰는것은 다른 API를 만들때 문제가 된다.
        return memberService.findAllMember();
    }


    @GetMapping("/api/v2/members")
    public Result getMembersV2() {
        List<Member> findAllMembers = memberService.findAllMember();
        List<MemberDto> collect = findAllMembers
                .stream()
                .map(m -> new MemberDto(m.getUsername()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> { // 이런식으로 배열로 감싸서 json으로 반환해야 확장성이 보장된다.
        private int count;
        private T data;
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setUsername(request.name);
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request)
    {
        memberService.update(id, request.getName());
        // 커멘드와 쿼리를 분리해주기 위해서 service단이 아닌 여기서 다시 쿼리를 짜준다. service에서 return을 member로 하지 않은 이유이다.
        Member findMember = memberService.findOneMember(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getUsername());
    }

    @Data
    static class UpdateMemberRequest {
        private  String name;
    }

    @Data
    @AllArgsConstructor // 엔티티에서는 어노테이션을 최소로, dto는 조금 막 사용해도 된다.
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }


    @Data
    static class CreateMemberRequest{
        private String name; // postman json에도 username이 아닌 name으로 작성해야한다.
    }


    @Data
    static class CreateMemberResponse {
        @NotEmpty
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
