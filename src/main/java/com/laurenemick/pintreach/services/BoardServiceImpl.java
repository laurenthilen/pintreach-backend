package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.exceptions.ResourceNotFoundException;
import com.laurenemick.pintreach.models.Article;
import com.laurenemick.pintreach.models.Board;
import com.laurenemick.pintreach.models.BoardArticles;
import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.repositories.ArticleRepository;
import com.laurenemick.pintreach.repositories.BoardRepository;
import com.laurenemick.pintreach.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "boardService")
public class BoardServiceImpl
    implements BoardService
{
    @Autowired
    private BoardRepository boardrepos;

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private ArticleRepository articlerepos;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Board> findAllByUserId(Long userid)
    {
        return boardrepos.findAllByUser_Userid(userid);
    }
    @Override
    public Board findBoardById(long id)
    {
        return boardrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Board id " + id + " not found!"));
    }

    @Transactional
    @Override
    public Board save(
        User user,
        Article article)
    {
        Board newBoard = new Board();

        User dbuser = userrepos.findById(user.getUserid())
            .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found"));
        newBoard.setUser(dbuser);

        Article dbarticle = articlerepos.findById(article.getArticleid())
            .orElseThrow(() -> new ResourceNotFoundException("Article id " + article.getArticleid() + " not found"));

        BoardArticles newBoardArticle = new BoardArticles();
        newBoardArticle.setBoard(newBoard);
        newBoardArticle.setArticle(dbarticle);

        return boardrepos.save(newBoard);
    }

    @Transactional
    @Override
    public Board save(Board board,
                     Article article)
    {
        Board updateBoard = boardrepos.findById(board.getBoardid())
            .orElseThrow(() -> new ResourceNotFoundException("Board Id " + board.getBoardid() + " not found"));
        Article updateArticle = articlerepos.findById(article.getArticleid())
            .orElseThrow(() -> new ResourceNotFoundException("Article id " + article.getArticleid() + " not found"));

        boardrepos.addBoardArticles(userAuditing.getCurrentAuditor()
            .get(), updateBoard.getBoardid(), updateArticle.getArticleid());

        return boardrepos.save(updateBoard);
    }

    @Transactional
    @Override
    public void delete(Board board,
                       Article article)
    {
        Board updateBoard = boardrepos.findById(board.getBoardid())
            .orElseThrow(() -> new ResourceNotFoundException("Board Id " + board.getBoardid() + " not found"));
        Article updateArticle = articlerepos.findById(article.getArticleid())
            .orElseThrow(() -> new ResourceNotFoundException("Article id " + article.getArticleid() + " not found"));

        boardrepos.removeBoardWithNoArticles();
        throw new ResourceNotFoundException("Board id " + updateBoard.getBoardid() + " Article id " + updateArticle.getArticleid() + " combo not found");
    }
}