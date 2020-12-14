package com.laurenemick.pintreach.repositories;

import com.laurenemick.pintreach.models.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long>
{
}
