package by.kopetcev.shop.repositories;

import by.kopetcev.shop.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@Sql(scripts = {"/sql/cleanup.sql", "/sql/insert_TagRepository.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/cleanup.sql"}, executionPhase = AFTER_TEST_METHOD)
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void shouldFindOne() {
        Optional<Tag> found = tagRepository.findById(4L);
        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(new Tag(4L, "find_me")));
    }

    @Test
    void shouldCreate() {
        Tag newTag = new Tag("created Tag");
        assertThat(newTag.getId(), nullValue());

        Tag created = tagRepository.save(newTag);
        assertThat(created.getName(), equalTo(newTag.getName()));
        assertThat(created.getId(), notNullValue());

        Optional<Tag> actual = tagRepository.findById(created.getId());
        assertThat(actual.isPresent(), is(true));
        assertThat(created, equalTo(actual.get()));
    }

    @Test
    void shouldNotFindOneNotExistent() {
        Optional<Tag> found = tagRepository.findById(1000L);
        assertThat(found.isPresent(), is(false));
    }

    @Test
    void shouldFindAll() {
        Iterable<Tag> all = tagRepository.findAll();
        assertThat(all, hasItems(new Tag(1L, "electronics"),
                new Tag(2L, "computers"),
                new Tag(3L, "home appliances")));
    }

    @Test
    void shouldDelete() {
        long id = 5L;
        assertThat(tagRepository.findById(id).isPresent(), is(true));
        tagRepository.deleteById(id);
        assertThat(tagRepository.findById(id).isPresent(), is(false));
    }
}
