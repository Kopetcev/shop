package by.kopetcev.shop.controllers;

import by.kopetcev.shop.model.Item;
import by.kopetcev.shop.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name="Item", description="Item API")
public class ItemController {

    private final ItemService itemService;

    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "get all items")
    public List<Item> all() {
        return itemService.findAll();
    }

    @PostMapping("/items")
    @Operation(summary = "create new item")
    @PreAuthorize("hasAuthority('write')")
    public Item newItem(@RequestBody @Parameter(description = "new item") Item newItem) {
        return itemService.save(newItem);
    }

    @GetMapping("/items/{id}")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "get item")
    public Item one(@PathVariable @Parameter(description = "item id") Long id) {
        return itemService.findById(id);
    }

    @PutMapping("/items/{id}")
    @PreAuthorize("hasAuthority('write')")
    @Operation(summary = "update item", description = "allows to update cart, if item is in cart use force = true")
    public Item updateItem(@RequestBody @Parameter(description = "updated item") Item updatedItem,
                    @PathVariable @Parameter(description = "item id") Long id,
                    @RequestParam(value = "force", required = false) @Parameter(description = "force") boolean isForce) {
        return itemService.replace(updatedItem, id, isForce);
    }

    @DeleteMapping("/items/{id}")
    @PreAuthorize("hasAuthority('write')")
    @Operation(summary = "delete item")
    public void deleteItem(@PathVariable @Parameter(description = "Item id") Long id) {
        itemService.deleteById(id);
    }

    @GetMapping("/items/filter")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "search item by template")
    public List<Item> searchByTagFilter( @RequestParam(value = "tags", required = false) @Parameter(description = "Tags for filter") List<String> tags,  @RequestParam(value = "contains", required = false) String contains) {
        return itemService.findBy(tags, contains);
    }
}


