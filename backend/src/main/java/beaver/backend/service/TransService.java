package beaver.backend.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import beaver.backend.SearchCriteria;
import beaver.backend.TransSpecification;
import beaver.backend.domain.Trans;
import beaver.backend.repository.TransRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TransService {
    private final TransRepository transRepository;

    public Trans findById(long id) {
        return transRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public List<Trans> searchTrans(SearchCriteria criteria) {
    Specification<Trans> spec = TransSpecification.findByCriteria(criteria);
    return transRepository.findAll(spec);
}
}

