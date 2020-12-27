package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.exceptions.ResourceNotFoundException;
import com.laurenemick.pintreach.models.*;
import com.laurenemick.pintreach.repositories.ArticleRepository;
import com.laurenemick.pintreach.repositories.BoardRepository;
import com.laurenemick.pintreach.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "boardService")
public class BoardServiceImpl
    implements BoardService
{
    @Autowired
    private BoardRepository boardrepos;

    @Autowired
    private UserService userService;

    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<Board> findAll()
    {
        List<Board> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        boardrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Board> findAllByUserId(long userid)
    {
        return boardrepos.findAllByUser_Userid(userid);
    }
    @Override
    public Board findBoardById(long id)
    {
        return boardrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Board id " + id + " not found!"));
    }

    @Override
    public void addNewBoard(Board newBoard, User user) {
        long userId = newBoard.getBoardid();

        Board addedBoard = new Board();

        addedBoard.setThumbnail(newBoard.getThumbnail());
        addedBoard.setDescription(newBoard.getDescription());
        addedBoard.setTitle(newBoard.getTitle());
        addedBoard.setBoardid(0);
        addedBoard.setUser(user);
        newBoard = boardrepos.save(addedBoard);
    }

    @Transactional
    @Override
    public Board save(Board board)
    {
//        if (board.getBoardid() != 0)
//        {
//            boardrepos.findById(board.getBoardid())
//                .orElseThrow(() -> new ResourceNotFoundException("Board id " + board.getBoardid() + " not found!"));
//            newBoard.setBoardid(board.getBoardid());
//        }

        Board newBoard = new Board();

        // Set local fields
        newBoard.setTitle(board.getTitle());
        newBoard.setDescription(board.getDescription());
        newBoard.setThumbnail(board.getThumbnail());

        // Set joins
        newBoard.setUser(board.getUser());
        newBoard.setArticles(board.getArticles());

        return boardrepos.save(newBoard);
    }

    @Transactional
    @Override
    public Board update(long boardId, Board board) {
        // Get current board object from DB
        Board currentBoard = boardrepos.findById(boardId).orElseThrow(()->new ResourceNotFoundException("Board " + boardId + " not found"));;

//        // Check if current user is authorized to make change
//        if (
//            helperFunctions.isAuthorizedToMakeChange(board.getUser().getUsername())
//        ) {
            // If the incoming object has name, update
            if (board.getTitle() != null) {
                currentBoard.setTitle(board.getTitle());
            }

            // If the incoming object has description, update
            if (board.getDescription() != null) {
                currentBoard.setDescription(board.getDescription());
            }

            // If incoming object has thumbnail, update
            if (board.getThumbnail() != null) {
                currentBoard.setThumbnail(board.getThumbnail());
            }

            // Save updated board to DB
            return boardrepos.save(currentBoard);

//        } else {
//            // note we should never get to this line but is needed for the compiler
//            // to recognize that this exception can be thrown
//            throw new ResourceNotFoundException(
//                "This user is not authorized to make change"
//            );
//        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        boardrepos.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        boardrepos.deleteAll();
    }
}