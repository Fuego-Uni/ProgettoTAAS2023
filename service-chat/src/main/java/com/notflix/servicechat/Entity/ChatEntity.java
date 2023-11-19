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

    // set user 1
    public void setUser1(UserEntity user1) {
        this.user1 = user1;
    }
    // set user 2
    public void setUser2(UserEntity user2) {
        this.user2 = user2;
    }
    // getid
    public Long getId() {
        return id;
    }
}
