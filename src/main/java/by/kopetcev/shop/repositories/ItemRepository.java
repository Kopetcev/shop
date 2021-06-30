package by.kopetcev.shop.repositories;

import by.kopetcev.shop.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    List<Item> findByDescriptionLikeIgnoreCaseOrderByName(String contains);

    List<Item> findDistinctByTagsNameInOrderByName(List<String> tags);

    List<Item> findAllByTagsNameInAndDescriptionLikeIgnoreCaseOrderByName(List<String> tags, String contains);
}
