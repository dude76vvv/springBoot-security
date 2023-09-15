package com.example.authDemo.repository;

import com.example.authDemo.models.tokens.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {


    //to use for revoking all the past tokens before generating a new one when login
    //through userID. the id is fk of UserEntity
    @Query(value = """
      select t from Token t inner join UserEntity u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Integer id);

    //check if token exist by token name
    Optional<Token> findByToken(String token);


}
