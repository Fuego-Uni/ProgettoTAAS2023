package com.notflix.serviceauth.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "chat")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonManagedReference
    private UserEntity user1;

    @ManyToOne
    @JsonManagedReference
    private UserEntity user2;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
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
