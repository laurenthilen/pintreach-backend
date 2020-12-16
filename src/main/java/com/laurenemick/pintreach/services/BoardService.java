package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.models.Article;
import com.laurenemick.pintreach.models.Board;
import com.laurenemick.pintreach.models.User;

import java.util.List;

public interface BoardService
{
    List<Board> findAllByUserId(long userid);

    Board findBoardById(long id);

    Board save(
        User user,
        Article article);

    Board save(Board board,
              Article article);

    void delete(
        Board board,
        Article article);
}
