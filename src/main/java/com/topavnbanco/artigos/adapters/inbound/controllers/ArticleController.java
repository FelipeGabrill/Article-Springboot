package com.topavnbanco.artigos.adapters.inbound.controllers;


import com.topavnbanco.artigos.adapters.inbound.dtos.article.ArticleDTO;
import com.topavnbanco.artigos.adapters.inbound.dtos.article.ArticleSimpleDTO;
import com.topavnbanco.artigos.application.usecases.ArticleUseCases;
import com.topavnbanco.artigos.infrastructure.queryfilters.ArticleQueryFilter;
import com.topavnbanco.artigos.application.servicies.ArticleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Article", description = "Endpoints for managing article")
@RestController
@RequestMapping(value = "/articles", produces = "application/json")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {

    @Autowired
    private ArticleUseCases service;

    @Operation(
            summary = "Get Article by ID",
            description = "Retrieve a article by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT', 'ROLE_REVIEWER')")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleSimpleDTO> findById(@PathVariable Long id) {
        ArticleSimpleDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Get Article with body by ID",
            description = "Retrieve a article with body by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT', 'ROLE_REVIEWER')")
    @GetMapping("/{id}/body")
    public ResponseEntity<String> findArticleBodyById(@PathVariable Long id) {
        String body = service.findArticleBodyById(id);
        return ResponseEntity.ok(body);
    }

    @Operation(
            summary = "List all Article",
            description = "Retrieve all article with optional filters and pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT', 'ROLE_REVIEWER')")
    @GetMapping
    public ResponseEntity<Page<ArticleSimpleDTO>> findAll(ArticleQueryFilter articleQueryFilter, Pageable pageable) {
        Page<ArticleSimpleDTO> dto = service.findAll(articleQueryFilter, pageable);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "List top 20 Article",
            description = "Retrieve top 20 article with optional filters and pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT', 'ROLE_REVIEWER')")
    @GetMapping("top20/{congressoId}")
    public ResponseEntity<Page<ArticleSimpleDTO>> findTop20(@PathVariable Long congressoId) {
        Page<ArticleSimpleDTO> dto = service.findTop20(congressoId);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Create a new Article",
            description = "Register a new article in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT')")
    @PostMapping
    public ResponseEntity<ArticleSimpleDTO> insert(@Valid @RequestBody ArticleDTO dto) {
        ArticleSimpleDTO simpleDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(simpleDto);
    }

    @Operation(
            summary = "Update a Article",
            description = "Update the details of an existing article",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT')")
    @PutMapping("/{id}")
    public ResponseEntity<ArticleSimpleDTO> update(@PathVariable Long id, @Valid @RequestBody ArticleDTO dto) {
        ArticleSimpleDTO simpleDto = service.update(id, dto);
        return ResponseEntity.ok(simpleDto);
    }

    @Operation(
            summary = "Delete a Article",
            description = "Delete a article by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
