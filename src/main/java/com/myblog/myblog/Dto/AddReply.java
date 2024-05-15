package com.myblog.myblog.Dto;

import com.myblog.myblog.Entity.Board;
import com.myblog.myblog.Entity.Reply;
import com.myblog.myblog.Entity.User;
import lombok.Data;

@Data
public class AddReply {

    private Long boardId;
    private String content;
    private String author;


}
