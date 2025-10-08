package com.topavnbanco.artigos.adapters.inbound.controllers;


import com.topavnbanco.artigos.adapters.inbound.dtos.review.ReviewDTO;
import com.topavnbanco.artigos.infrastructure.queryfilters.ReviewQueryFilter;
import com.topavnbanco.artigos.application.servicies.review.ReviewServiceImpl;
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

@Tag(name = "Review", description = "Endpoints for managing review")
@RestController
@RequestMapping(value = "/reviews", produces = "application/json")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {

    @Autowired
    private ReviewServiceImpl service;

    @Operation(
            summary = "Get Review by ID",
            description = "Retrieve a review by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT', 'ROLE_REVIEWER')")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> findById(@PathVariable Long id) {
        ReviewDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "List all Review",
            description = "Retrieve all review with optional filters and pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT', 'ROLE_REVIEWER')")
    @GetMapping
    public ResponseEntity<Page<ReviewDTO>> findAll(ReviewQueryFilter reviewQueryFilter, Pageable pageable) {
        Page<ReviewDTO> dto = service.findAll(reviewQueryFilter, pageable);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Update a Review",
            description = "Update the details of an existing review",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REVIEWER')")
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> update(@PathVariable Long id, @Valid @RequestBody ReviewDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Delete a Review",
            description = "Delete a review by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REVIEWER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
