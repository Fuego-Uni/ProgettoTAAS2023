package com.notflix.servicechat.Entity;

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

   /*  @Column(name = "chat_name")
    private String chatName; */

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
    // get user 1
    public UserEntity getUser1() {
        return user1;
    }
    // get user 2
    public UserEntity getUser2() {
        return user2;
    }
}
