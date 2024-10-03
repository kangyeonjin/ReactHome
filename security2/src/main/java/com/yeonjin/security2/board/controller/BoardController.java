package com.yeonjin.security2.board.controller;

import com.yeonjin.security2.board.model.dto.CreateBoardDTO;
import com.yeonjin.security2.board.model.entity.Board;
import com.yeonjin.security2.board.service.BoardService;
import com.yeonjin.security2.member.model.entity.Member;
import com.yeonjin.security2.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/create")
    public String create(){
        return "board/createBoard";
    }

    @PostMapping("/create")
    public String createPost(@AuthenticationPrincipal UserDetails userDetails, CreateBoardDTO createBoardDTO){

        String memberId = userDetails.getUsername();

        Member member = memberService.findMemberById(memberId);

        log.info("로그인한 사용자 id:{}", memberId);
        log.info("전달받은 boarddto:{}", createBoardDTO);

        boardService.create(createBoardDTO, member);

        return "redirect:/";
    }

    @GetMapping("/{boardId}")
    public String getBoardDetail(@PathVariable int boardId, Model model){

        Board board = boardService.findBoardById(boardId);

        model.addAttribute("board", board);

        return "board/detail";
    }


}
