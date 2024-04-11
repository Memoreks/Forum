package org.example.forum.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.forum.dto.MessageDto;
import org.example.forum.dto.NewTopicDto;
import org.example.forum.dto.TopicDto;
import org.example.forum.services.MessageService;
import org.example.forum.services.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/topic")
public class TopicController {

    private TopicService topicService;
    private MessageService messageService;

    public TopicController(TopicService topicService, MessageService messageService) {
        this.topicService = topicService;
        this.messageService = messageService;

        //Тестовые данные
        topicService.create(new TopicDto(1L, "Погода за окном"));
        topicService.create(new TopicDto(2L, "Рецепты блюд"));
        topicService.create(new TopicDto(3L, "Места для путешествия"));
        messageService.create(new MessageDto(1L, "Alex", "Какая у вас погода за окном?",
                Instant.now(), 1L));
        messageService.create(new MessageDto(2L, "Ivan", "Отличная, солнце!",
                Instant.now(), 1L));
        messageService.create(new MessageDto(3L, "Alex", "Поделитесь рецептом пирогов?",
                Instant.now(), 2L));
        messageService.create(new MessageDto(4L, "Alex", "Рекомендую всем побывать на Байкале!",
                Instant.now(), 3L));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        TopicDto topicDto = topicService.getById(id);

        if (topicDto == null) {
            return new ResponseEntity<>("Invalid topic ID", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(topicDto, HttpStatus.OK);
        }
    }

    @GetMapping
    public Collection<TopicDto> getAll() {
        return topicService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewTopicDto newTopicDto) {
        if (newTopicDto == null) {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }
        if (newTopicDto.getTopic() == null || newTopicDto.getMessage() == null) {
            return new ResponseEntity<>("Validation exception", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        TopicDto topicDto = topicService.create(newTopicDto.getTopic());
        MessageDto messageDto = newTopicDto.getMessage();
        messageDto.setTopicId(topicDto.getId());
        messageDto = messageService.create(messageDto);
        return new ResponseEntity<>("Successful operation", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody TopicDto topicDto) {
        if (topicDto == null) {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }
        topicDto = topicService.update(topicDto);
        return new ResponseEntity<>("Successful operation", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @RequestBody String nick) {

        TopicDto topicDto = topicService.deleteById(id, nick);

        if (topicDto == null) {
            return new ResponseEntity<>("Invalid topic ID", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}