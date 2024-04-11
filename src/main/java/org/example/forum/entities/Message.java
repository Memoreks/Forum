package org.example.forum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nick")
    private String nick;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}