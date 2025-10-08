package com.topavnbanco.artigos.adapters.inbound.controllers;


import com.topavnbanco.artigos.adapters.inbound.dtos.evaluation.EvaluationDTO;
import com.topavnbanco.artigos.application.servicies.EvaluationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Evaluation", description = "Endpoints for managing evaluation")
@RestController
@RequestMapping(value = "/evaluations", produces = "application/json")
@SecurityRequirement(name = "bearerAuth")
public class EvaluationController {

    @Autowired
    private EvaluationServiceImpl service;

    @Operation(
            summary = "Get Evaluation by ID",
            description = "Retrieve a evaluation by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT', 'ROLE_REVIEWER')")
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationDTO> findById(@PathVariable Long id) {
        EvaluationDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "List all Evaluation",
            description = "Retrieve all evaluation with optional filters and pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTICIPANT', 'ROLE_REVIEWER')")
    @GetMapping
    public ResponseEntity<Page<EvaluationDTO>> findAll(Pageable pageable) {
        Page<EvaluationDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Delete a Evaluation",
            description = "Delete a evaluation by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
