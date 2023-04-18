package jpabook.jpashop.api;

import jpabook.jpashop.api.reponse.Result;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * api 요청 스펙에 맞춰서 별도의 Dto를 만들어야한다. @Valid도 Dto
     * 절대 Entity를 외부에 노출해서도 안된다.
     * 어느값이 파라미터로 넘어오는지 알 수 가 없다.
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * api 스펙이 바뀌지 않는다.
     * api 스펙에 fit하게 맞출 수 있다.
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getUsername());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * style이지만 command와 query를 분리.
     */
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMemberRequest request
    ) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @GetMapping("/api/v1/members")
    public List<Member> memberListV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberListV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(toList());

        return new Result(collect);
    }

    @Getter
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class UpdateMemberRequest {
        private String name;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class CreateMemberRequest {
        private String username;
    }


    @Getter
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }
}
