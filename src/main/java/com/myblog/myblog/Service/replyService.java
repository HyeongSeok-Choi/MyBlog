package com.myblog.myblog.Service;

import com.myblog.myblog.Dto.AddReply;
import com.myblog.myblog.Entity.Board;
import com.myblog.myblog.Entity.Reply;
import com.myblog.myblog.Entity.User;
import com.myblog.myblog.Entity.guestBook;
import com.myblog.myblog.Repository.ReplyRepository;
import com.myblog.myblog.Repository.boardRepository;
import com.myblog.myblog.Repository.guestBookRepository;
import com.myblog.myblog.Repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class replyService {


    private final ReplyRepository replyRepository;

    private final userRepository userRepository;

    private final guestBookRepository guestbookRepository;

    public Reply addReply(AddReply addReply) {

        User user = userRepository.findByEmail(addReply.getAuthor());


        guestBook guestbook= guestbookRepository.findById(addReply.getBoardId()).get() ;



        Reply reply = new Reply();

        reply.setBoardId(guestbook);
        reply.setAuthor(user);
        reply.setReply(addReply.getContent());

       Reply savedReply= replyRepository.save(reply);

        return savedReply;

    }
/*
    public List<Reply> getReplies(Long boardId) {

        List<Reply> replyList= replyRepository.findByBoardId(boardId);

        return replyList;
    }

 */
}
