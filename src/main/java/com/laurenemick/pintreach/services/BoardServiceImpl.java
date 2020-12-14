package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.models.Board;
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
//    @Autowired
//    private BoardRepository boardrepos;
//
//    @Autowired
//    private UserRepository userrepos;
//
//    @Autowired
//    private ArticleRepository articlerepos;
//
//    @Autowired
//    private UserAuditing userAuditing;
//
//    @Override
//    public List<Board> findAllByUserId(Long userid)
//    {
//        return boardrepos.findAllByUser_Userid(userid);
//    }

}
