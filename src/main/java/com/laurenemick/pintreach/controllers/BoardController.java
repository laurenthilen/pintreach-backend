package com.laurenemick.pintreach.controllers;

import com.laurenemick.pintreach.models.Board;
import com.laurenemick.pintreach.services.HelperFunctions;
import com.laurenemick.pintreach.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController
{
    @Autowired
    private BoardService boardService;

    @Autowired
    private HelperFunctions helperFunctions;

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

    @PostMapping(value = "/board", consumes = {"application/json"})
    public ResponseEntity<?> addNewBoard(@Valid
                                         @RequestBody Board newBoard) throws URISyntaxException
    {
        // always post as current user
        newBoard.setUser(helperFunctions.getCurrentUser());

        newBoard = boardService.save(newBoard);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newBoardURI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{boardId}")
            .buildAndExpand(newBoard.getBoardid())
            .toUri();
        responseHeaders.setLocation(newBoardURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable long boardId, @RequestBody Board updateBoard)
    {
        boardService.update(boardId, updateBoard);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/board/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable long id)
    {
        boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
