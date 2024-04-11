package org.example.forum.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.forum.dto.TopicDto;
import org.example.forum.entities.Topic;
import org.example.forum.repositories.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicService implements CRUDService<TopicDto> {

    private final TopicRepository repository;

    @Override
    public TopicDto getById(Long id) {
        log.info("Get topic by ID: " + id);
        Topic topic = repository.findById(id).orElse(null);
        if(topic == null) {
            return null;
        }
        return mapToDto(topic);
    }

    @Override
    public Collection<TopicDto> getAll() {
        log.info("Get all topics");
        return repository.findAll().stream()
                .map(topic -> mapToDto(topic))
                .toList();
    }

    @Override
    public TopicDto create(TopicDto topicDto) {
        log.info(topicDto.toString());
        log.info("Create topic");
        Topic topic = mapToEntity(topicDto);
        repository.save(topic);
        return mapToDto(topic);
    }

    @Override
    public TopicDto update(TopicDto topicDto) {
        log.info("Update topic");
        Topic topic = mapToEntity(topicDto);
        repository.save(topic);
        return mapToDto(topic);
    }

    @Override
    public TopicDto deleteById(Long id, String nick) {
        log.info("Delete topic by id " + id);
        Topic topic = repository.findById(id).orElse(null);
        if(topic == null) {
            return null;
        }
        repository.deleteById(id);
        return mapToDto(topic);
    }

    public TopicDto mapToDto(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setTitle(topic.getTitle());
        return topicDto;
    }

    public Topic mapToEntity(TopicDto topicDto) {
        Topic topic = new Topic();
        topic.setId(topicDto.getId());
        topic.setTitle(topicDto.getTitle());
        return topic;
    }
}