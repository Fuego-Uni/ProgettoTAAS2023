package com.notflix.servicechat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.notflix.servicechat.Entity.MessageEntity;
import java.util.List;


public interface MessageEntityRepo extends JpaRepository<MessageEntity, Long> {
  List<MessageEntity> findAllByChat_Id(Long chatId);
}

