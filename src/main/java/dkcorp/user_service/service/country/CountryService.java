package dkcorp.user_service.service.country;

import dkcorp.user_service.entity.Country;

public interface CountryService {
    Country findById(long countryId);
}
