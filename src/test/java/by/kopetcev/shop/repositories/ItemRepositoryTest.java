package by.kopetcev.shop.repositories;

import by.kopetcev.shop.model.Item;
import by.kopetcev.shop.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@Sql(scripts = {"/sql/cleanup.sql", "/sql/insert_ItemRepository.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/cleanup.sql"}, executionPhase = AFTER_TEST_METHOD)
@Transactional
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void shouldFindByDescriptionLikeIgnoreCaseOrderByName() {
        String template = "%BEST%";
        List<Item> result = itemRepository.findByDescriptionLikeIgnoreCaseOrderByName(template);
        assertThat(result, hasSize(2));
        assertThat(result, contains(new Item(5L, "car alarm", "The best loud protection of your car", Set.of(new Tag(1L, "electronics"))) ,
                new Item(4L, "vacuum cleaner", "The best vacuum cleaner on the market", Set.of(new Tag(3L, "home appliances")))));
    }

    @Test
    void shouldFindDistinctByTagsNameInOrderByName() {
        Tag tag1 = new Tag(1L, "electronics");
        Tag tag3 = new Tag(3L, "home appliances");
        Tag tag5 = new Tag(5L, "entertainments");
        List<String> tags = Arrays.asList(tag3.getName(), tag5.getName());
        List<Item> result = itemRepository.findDistinctByTagsNameInOrderByName(tags);
        assertThat(result, hasSize(3));
        assertThat(result, contains(new Item (2L, "TV", "TV with a large CRT screen", Set.of(tag5)),
                new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(tag1, tag5)),
                        new Item(4L, "vacuum cleaner", "The best vacuum cleaner on the market", Set.of(tag3))));
    }

    @Test
    void shouldFindAllByTagsNameInAndDescriptionLikeIgnoreCaseOrderByName() {
        Tag tag1 = new Tag(1L, "electronics");
        Tag tag3 = new Tag(3L, "home appliances");
        Tag tag5 = new Tag(5L, "entertainments");
        String template = "%BEST%";
        List<String> tags = Arrays.asList(tag1.getName(), tag3.getName(), tag5.getName());
        List<Item> result = itemRepository.findAllByTagsNameInAndDescriptionLikeIgnoreCaseOrderByName(tags, template);
        assertThat(result, hasSize(2));
        assertThat(result, contains(new Item(5L, "car alarm", "The best loud protection of your car", Set.of(tag1)),
                new Item(4L, "vacuum cleaner", "The best vacuum cleaner on the market", Set.of(tag3))));

    }
}


