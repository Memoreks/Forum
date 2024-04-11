package org.example.forum.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NewTopicDto {

    private TopicDto topic;
    private MessageDto message;
}
