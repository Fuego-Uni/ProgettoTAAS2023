package com.notflix.servicechat.repo;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.notflix.servicechat.Entity.ChatEntity;

public interface ChatEntityRepo extends JpaRepository<ChatEntity, Long> {
  Optional<ChatEntity> findByUser1(String user);
  Optional<ChatEntity> findByUser2(String user);

  // error: line 1:7 no viable alternative at input 'SELECT*'
  // findby user1 or by user2
  @Query("SELECT c FROM ChatEntity c WHERE c.user1 = ?1 OR c.user2 = ?1")
  List<ChatEntity> findByUser1OrUser2(String user);
}   

