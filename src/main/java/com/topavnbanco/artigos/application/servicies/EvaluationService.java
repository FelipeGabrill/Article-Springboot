package com.topavnbanco.artigos.application.servicies;

import com.topavnbanco.artigos.domain.evaluation.EvaluationDTO;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import com.topavnbanco.artigos.adapters.outbound.repositories.EvaluationRepository;
import com.topavnbanco.artigos.application.servicies.exceptions.DatabaseException;
import com.topavnbanco.artigos.application.servicies.exceptions.ResourceNotFoundException;
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
