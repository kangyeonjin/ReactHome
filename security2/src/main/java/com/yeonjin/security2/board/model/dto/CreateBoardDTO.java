package com.yeonjin.security2.board.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CreateBoardDTO {

    private String title;
    private String content;
}
