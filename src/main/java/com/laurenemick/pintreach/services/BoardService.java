package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.models.Article;
import com.laurenemick.pintreach.models.Board;
import com.laurenemick.pintreach.models.User;

import java.util.List;

public interface BoardService
{
    List<Board> findAllByUserId(long userid);

    Board findBoardById(long id);

    List<Board> findAll();

    Board save(Board board);

    void addNewBoard(Board newBoard, User user);

    Board update(
        long id,
        Board Board);

    void delete(long id);

    void deleteAll();
}
