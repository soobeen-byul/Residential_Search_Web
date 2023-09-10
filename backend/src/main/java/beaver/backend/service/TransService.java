package beaver.backend.service;

import org.springframework.stereotype.Service;

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
}