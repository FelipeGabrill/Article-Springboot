package com.topavnbanco.artigos.adapters.inbound.controllers;


import com.topavnbanco.artigos.domain.card.CardDTO;
import com.topavnbanco.artigos.application.servicies.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Card", description = "Endpoints for managing card")
@RestController
@RequestMapping(value = "/cards", produces = "application/json")
@SecurityRequirement(name = "bearerAuth")
public class CardController {

    @Autowired
    private CardService service;

    @Operation(
            summary = "Get Card by ID",
            description = "Retrieve a card by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT', 'ROLE_REVIEWER')")
    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> findById(@PathVariable Long id) {
        CardDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Update a Card",
            description = "Update the details of an existing card",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT', 'ROLE_REVIEWER')")
    @PutMapping("/{id}")
    public ResponseEntity<CardDTO> update(@PathVariable Long id, @Valid @RequestBody CardDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

}
