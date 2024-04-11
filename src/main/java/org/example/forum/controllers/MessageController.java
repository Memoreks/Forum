package org.example.forum.controllers;

import org.example.forum.dto.MessageDto;
import org.example.forum.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        MessageDto messageDto = messageService.getById(id);

        if (messageDto == null) {
            return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(messageDto, HttpStatus.OK);
        }
    }

    @GetMapping
    public Collection<MessageDto> getAll() {
        return messageService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MessageDto messageDto) {
        messageDto = messageService.create(messageDto);
        return new ResponseEntity<>(messageDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody MessageDto messageDto) {
        if (messageDto == null) {
            return new ResponseEntity<>("Invalid ID supplied", HttpStatus.BAD_REQUEST);
        }
        messageDto = messageService.update(messageDto);
        if (messageDto == null) {
            return new ResponseEntity<>("Topic not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Successful operation", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @RequestBody String nick) {

        MessageDto messageDto = messageService.deleteById(id, nick);

        if (messageDto == null) {
            return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/topic/{id}")
    public Collection<MessageDto> getTopicById(@PathVariable Long id) {
        return messageService.getAllMessageInTopic(id);
    }
}