package com.notflix.servicechat.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notflix.servicechat.Entity.ChatEntity;

public interface ChatEntityRepo extends JpaRepository<ChatEntity, Long> {
    //findby User1
    Optional<ChatEntity> findByUser1(Long user1);
    //findby User2
    Optional<ChatEntity> findByUser2(Long user2);
    // findby user1 or by user2
    Optional<ChatEntity> findByUser1OrUser2(Long user1, Long user2);
}   

