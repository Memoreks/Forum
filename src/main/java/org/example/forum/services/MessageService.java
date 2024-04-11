package org.example.forum.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.forum.dto.MessageDto;
import org.example.forum.entities.Message;
import org.example.forum.repositories.TopicRepository;
import org.example.forum.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService implements CRUDService<MessageDto>{

    private final MessageRepository repository;
    private final TopicRepository topicRepository;

    public MessageDto getById(Long id) {
        log.info("Get message by ID: " + id);
        Message message = repository.findById(id).orElse(null);
        if(message == null) {
            return null;
        }
        return mapToDto(message);
    }

    public Collection<MessageDto> getAll() {
        log.info("Get all messages");
        return repository.findAll().stream()
                .map(message -> mapToDto(message))
                .toList();
    }

    public Collection<MessageDto> getAllMessageInTopic(Long id) {
        log.info("Get all messages in topic id " + id);
        return repository.findAll().stream()
                .filter(message -> message.getTopic().getId() == id)
                .map(message -> mapToDto(message))
                .toList();
    }

    public MessageDto create(MessageDto messageDto) {
        log.info(messageDto.toString());
        log.info("Create message");
        messageDto.setDate(Instant.now());
        Message message = mapToEntity(messageDto);
        repository.save(message);
        return mapToDto(message);
    }

    public MessageDto update(MessageDto messageDto) {
        log.info("Update message");
        Message message = mapToEntity(messageDto);
        messageDto.setDate(Instant.now());
        Message oldMessage = repository.findById(message.getId()).orElse(null);
        if (!oldMessage.getNick().equals(message.getNick())) {
            return null;
        }
        repository.save(message);
        return mapToDto(message);
    }

    public MessageDto deleteById(Long id, String nick) {
        log.info("Delete message by id " + id);
        Message message = repository.findById(id).orElse(null);
        if (message == null) {
            return null;
        }
        if (!message.getNick().equals(nick)) {
            return null;
        }
        repository.deleteById(id);
        return mapToDto(message);
    }

    public MessageDto mapToDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setNick(message.getNick());
        messageDto.setText(message.getText());
        messageDto.setDate(message.getDate());
        messageDto.setTopicId(message.getTopic().getId());
        return messageDto;
    }

    public Message mapToEntity(MessageDto messageDto) {
        Message message = new Message();
        message.setId(messageDto.getId());
        message.setNick(messageDto.getNick());
        message.setText(messageDto.getText());
        message.setDate(messageDto.getDate());
        message.setTopic(topicRepository.findById(messageDto.getTopicId()).orElseThrow());
        return message;
    }
}