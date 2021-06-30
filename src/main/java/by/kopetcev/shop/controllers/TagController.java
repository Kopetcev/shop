package by.kopetcev.shop.controllers;

import by.kopetcev.shop.model.Tag;
import by.kopetcev.shop.services.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name="Tag", description="Tag API")
public class TagController {

    private final TagService tagService;

    TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "get all tag")
    public List<Tag> all() {
        return tagService.findAll();
    }

    @PostMapping("/tags")
    @Operation(summary = "create new tag")
    @PreAuthorize("hasAuthority('write')")
    public Tag newTag(@RequestBody @Parameter(description = "new tag") Tag newTag) {
        return tagService.save(newTag);
    }

    @GetMapping("/tags/{id}")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "get tag")
    public Tag one(@PathVariable @Parameter(description = "tag id") Long id) {
        return tagService.findById(id);
    }


    @DeleteMapping("/tags/{id}")
    @PreAuthorize("hasAuthority('write')")
    @Operation(summary = "delete tag")
    public void deleteItem(@PathVariable @Parameter(description = "tag id") Long id) {
        tagService.deleteById(id);
    }
}