package com.laurenemick.pintreach.repositories;

import com.laurenemick.pintreach.models.Board;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long>
{
    List<Board> findAllByUser_Userid(long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO boardarticles (boardid, articleid, created_by, created_date, last_modified_by, last_modified_date) VALUES (:boardid, :articleid, :uname, CURRENT_TIMESTAMP , :uname, CURRENT_TIMESTAMP)", nativeQuery = true)
    void addBoardArticles(String uname,
                      long boardid,
                      long articleid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM boards WHERE boardid NOT IN (SELECT boardid FROM boardarticles)", nativeQuery = true)
    void removeBoardWithNoArticles();
}
