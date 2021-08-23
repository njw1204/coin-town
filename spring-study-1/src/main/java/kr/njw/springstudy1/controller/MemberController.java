package kr.njw.springstudy1.controller;

import kr.njw.springstudy1.model.Member;
import kr.njw.springstudy1.service.MemberService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("")
    public String index() {
        return "member/index";
    }

    @GetMapping("signup")
    public String signUp() {
        return "member/signup";
    }

    @PostMapping("signup")
    public String createSignUp(@ModelAttribute MemberService.SignUpRequest signUpRequest) {
        Member member = this.memberService.signUp(signUpRequest);
        return "redirect:";
    }

    @PostMapping("signup/api")
    @ResponseBody
    public CreateSignUpApiResponse createSignUpApi(@RequestBody MemberService.SignUpRequest signUpRequest) {
        Member member = this.memberService.signUp(signUpRequest);

        return MemberController.CreateSignUpApiResponse.builder()
                .result(member != null)
                .data(member)
                .build();
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("members", this.memberService.findMembers());
        return "member/list";
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateSignUpApiResponse {
        @Builder.Default private boolean result = false;
        private Member data;
    }
}
