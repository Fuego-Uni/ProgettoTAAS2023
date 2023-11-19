package com.notflix.servicechat.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notflix.servicechat.Entity.ChatEntity;

public interface ChatEntityRepo extends JpaRepository<ChatEntity, Long> {
    Optional<ChatEntity> findByUser1_Id(String id);
    Optional<ChatEntity> findByUser2_Id(String id);
    // findby user1 or by user2
    //Optional<ChatEntity> findByUser1OrUser2(String user1, String user2);
}   

