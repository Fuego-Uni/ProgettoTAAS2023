package com.notflix.servicechat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.notflix.servicechat.Entity.MessageEntity;

public interface MessageEntityRepo extends JpaRepository<MessageEntity, Long> {
    // return all message by chat
    Iterable<MessageEntity> findAllByChatId(Long chatId);

}

