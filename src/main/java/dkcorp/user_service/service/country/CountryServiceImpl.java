package dkcorp.user_service.service.country;

import dkcorp.user_service.entity.Country;
import dkcorp.user_service.exception.EntityNotFoundException;
import dkcorp.user_service.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;


    @Override
    public Country findById(long countryId) {
        return countryRepository.findById(countryId).orElseThrow(() -> new EntityNotFoundException(String.format("Country with id=%d not found", countryId)));
    }
}
