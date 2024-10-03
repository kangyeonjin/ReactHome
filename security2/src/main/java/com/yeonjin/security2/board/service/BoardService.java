package com.yeonjin.security2.board.service;

import com.yeonjin.security2.board.model.dto.CreateBoardDTO;
import com.yeonjin.security2.board.model.entity.Board;
import com.yeonjin.security2.board.repository.BoardRepository;
import com.yeonjin.security2.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void create(CreateBoardDTO boardDTO, Member member){
        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .member(member)
                .build();

        Board savedBoard = boardRepository.save(board);
        log.info(""+savedBoard.getBoardId());
    }


    public Board findBoardById(int boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("게시글을찾을수없습니다"));
        return board;
    }

}
