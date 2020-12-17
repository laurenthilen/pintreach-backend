package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.models.Board;

import java.util.List;

public interface BoardService
{
    List<Board> findAllByUserId(long userid);

    Board findBoardById(long id);

    Board save(Board board);

    Board update(
        long id,
        Board Board);

    void delete(long id);
}
