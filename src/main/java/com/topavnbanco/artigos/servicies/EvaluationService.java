package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.dto.EvaluationDTO;
import com.topavnbanco.artigos.entities.Evaluation;
import com.topavnbanco.artigos.repositories.EvaluationRepository;
import com.topavnbanco.artigos.servicies.exceptions.DatabaseException;
import com.topavnbanco.artigos.servicies.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository repository;

    @Transactional(readOnly = true)
    public EvaluationDTO findById(Long id) {
        Evaluation evaluation = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new EvaluationDTO(evaluation);
    }

    @Transactional(readOnly = true)
    public Page<EvaluationDTO> findAll(Pageable pageable) {
        Page<Evaluation> result = repository.findAll(pageable);
        return result.map(x -> new EvaluationDTO(x));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
