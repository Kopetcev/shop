package by.kopetcev.shop.services;

import by.kopetcev.shop.model.Tag;
import by.kopetcev.shop.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TagService.class})
class TagServiceTest {

    @MockBean
    private TagRepository tagRepositoryMock;

    @Autowired
    private TagService service;

    @Test
    void shouldInvokeFindAll() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(1L,"tag1"));
        expected.add(new Tag(2L,"tag2"));
        when(tagRepositoryMock.findAll()).thenReturn(expected);
        assertThat(service.findAll(), equalTo(expected));
        Mockito.verify(tagRepositoryMock, Mockito.times(1)).findAll();
    }

    @Test
    void shouldInvokeSave() {
        Tag newTag = new Tag("tag");
        Tag expected = new Tag(1L, "tag");
        when(tagRepositoryMock.save(newTag)).thenReturn(expected);
        assertThat(service.save(newTag), equalTo(expected));
        Mockito.verify(tagRepositoryMock, Mockito.times(1)).save(newTag);
    }

    @Test
    void shouldInvokeFindById() {
        Optional<Tag> expected = Optional.of(new Tag(1L, "tag"));
        when(tagRepositoryMock.findById(expected.get().getId())).thenReturn(expected);
        assertThat(service.findById(expected.get().getId()), equalTo(expected.get()));
        Mockito.verify(tagRepositoryMock, Mockito.times(1)).findById(expected.get().getId());
    }

    @Test
    void shouldInvokeDeleteById() {
        Long id = 1L;
        service.deleteById(id);
        Mockito.verify(tagRepositoryMock, Mockito.times(1)).deleteById(id);
    }
}
