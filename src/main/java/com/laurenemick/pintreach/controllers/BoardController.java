package com.laurenemick.pintreach.controllers;

import com.laurenemick.pintreach.models.Article;
import com.laurenemick.pintreach.models.Board;
import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/boards")
public class BoardController
{
    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/user/{userId}", produces = {"application/json"})
    public ResponseEntity<?> listAllBoards(@PathVariable Long userId)
    {
        List<Board> myBoards = boardService.findAllByUserId(userId);
        return new ResponseEntity<>(myBoards, HttpStatus.OK);
    }

    @GetMapping(value = "/board/{boardId}",
        produces = {"application/json"})
    public ResponseEntity<?> getBoardById(
        @PathVariable
            Long boardId)
    {
        Board b = boardService.findBoardById(boardId);
        return new ResponseEntity<>(b,
            HttpStatus.OK);
    }

    @PostMapping(value = "/create/user/{userid}/article/{articleid}")
    public ResponseEntity<?> addNewBoard(@PathVariable long userid,
                                        @PathVariable long articleid)
    {
        User dataUser = new User();
        dataUser.setUserid(userid);

        Article dataArticle = new Article();
        dataArticle.setArticleid(articleid);

        boardService.save(dataUser, dataArticle);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/board/{boardid}/article/{articleid}")
    public ResponseEntity<?> updateBoard(@PathVariable long boardid,
                                        @PathVariable long articleid)
    {
        Board dataBoard = new Board();
        dataBoard.setBoardid(boardid);

        Article dataArticle = new Article();
        dataArticle.setArticleid(articleid);

        boardService.save(dataBoard, dataArticle);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/board/{boardid}/article/{articleid}")
    public ResponseEntity<?> deleteFromBoard(@PathVariable long boardid,
                                            @PathVariable long articleid)
    {
        Board dataBoard = new Board();
        dataBoard.setBoardid(boardid);

        Article dataArticle = new Article();
        dataArticle.setArticleid(articleid);

        boardService.delete(dataBoard, dataArticle);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
