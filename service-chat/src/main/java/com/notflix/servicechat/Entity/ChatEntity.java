package com.notflix.servicechat.Entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "chat")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user1;

    @ManyToOne
    private UserEntity user2;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<MessageEntity> messages;

}
