package com.myblog.myblog.Dto;


import com.myblog.myblog.Entity.Board;
import lombok.Data;

@Data
public class AddBoardRequest {

    private String title;
    private String content;
    private String userid;

    public Board toEntity(){
        Board board = new Board();
        
        board.setTitle(this.title);
        board.setContent(this.content);
        board.setUserid(this.userid);
        
        return board;
        
    }


}
