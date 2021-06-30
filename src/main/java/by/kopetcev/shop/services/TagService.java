package by.kopetcev.shop.services;

import by.kopetcev.shop.exception.ServiceException;
import by.kopetcev.shop.model.Tag;
import by.kopetcev.shop.repositories.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll() {
        return StreamSupport
                .stream(tagRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Tag save(Tag newTag) {
        return tagRepository.save(newTag);
            }

    public Tag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Tag with id =  " + id + " does not exist", HttpStatus.NOT_FOUND));

    }

    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}
