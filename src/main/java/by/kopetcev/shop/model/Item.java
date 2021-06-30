package by.kopetcev.shop.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="items")
@Schema(description = "Entity of item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Schema(description = "Detailed product description")
    private String description;

    @ManyToMany
    @JoinTable(name = "item_tags",
            joinColumns =
            @JoinColumn(name = "item_ref", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "tag_ref", referencedColumnName = "id"))
    @Schema(description = "Tags assigned to the item")
    private Set<Tag> tags;

    public Item() {
    }

    public Item(String name, String description, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public Item(Long id, String name, String description, Set<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(description, item.description) && Objects.equals(tags, item.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, tags);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                '}';
    }
}
