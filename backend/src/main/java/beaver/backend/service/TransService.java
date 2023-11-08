package beaver.backend.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import beaver.backend.domain.Location;
import beaver.backend.domain.Trans;
import beaver.backend.dto.Coordinates;
import beaver.backend.dto.SearchCriteria;
import beaver.backend.repository.LocationRepository;
import beaver.backend.repository.TransRepository;
import beaver.backend.specification.LocationSpecification;
import beaver.backend.specification.TransSpecification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TransService {
    private final TransRepository transRepository;
    private final LocationRepository locationRepository;
    private final AddressService addressService;


    public Trans findById(long id) {
        return transRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public List<Trans> searchTrans(SearchCriteria criteria) {

    Coordinates coordinates = addressService.getCoordinate(criteria.getAddress());
    Specification<Location> spec_loc = LocationSpecification.findByLocation(coordinates,criteria);
    List<Location> criteria_loc = locationRepository.findAll(spec_loc);
    
    Specification<Trans> spec_trans = TransSpecification.findByCriteria(criteria,criteria_loc);
    return transRepository.findAll(spec_trans);
}
}

