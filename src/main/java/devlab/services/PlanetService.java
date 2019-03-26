package devlab.services;


import devlab.mappers.PlanetMapper;
import devlab.models.Planet;
import devlab.models.dtos.PlanetDto;
import devlab.repositories.PlanetRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service  // warstwa biznesowa
public class PlanetService {

    private PlanetRepository planetRepository;
    private PlanetMapper planetMapper;

    public PlanetService(PlanetRepository planetRepository, PlanetMapper planetMapper) {
        this.planetRepository = planetRepository;
        this.planetMapper = planetMapper;
    }

    public List<PlanetDto> getPlanetDto() {
        return planetRepository
                .findAll()
                .stream()
                .map(planetMapper::map)
                .collect(Collectors.toList());
    }


    public List<PlanetDto> getPlanetDto(Long distance) {
        return planetRepository
                .findPlanetByDistanceFromSun(distance)
                .stream()
                .map(planetMapper::map)
                .collect(Collectors.toList());
    }

    public void deletePlanetByName(String name) {
        planetRepository.deletetByPlanetName(name);
    }

    public Planet addPlanet(PlanetDto planetDto) {
        return planetRepository.save(planetMapper.reverse(planetDto));

    }

    public void updatePlanet(PlanetDto planetDto) {
        planetRepository.
                findPlanetByPlanetName(planetDto.getPlanetName())
                .ifPresent(planet -> {
                            planet.setDistanceFromSun(planetDto.getDistanceFromSun());
                            planet.setOneWayLightTimeToTheSun(planetDto.getOneWayLightTimeToTheSun());
                            planet.setLengthOfYear(planetDto.getLengthOfYear());
                            planet.setPlanetType(planetDto.getPlanetType());
                            planet.setPlanetImage(planetDto.getPlanetImage());
                            planet.setPlanetInfo(planetDto.getPlanetInfo());
                            planetRepository.save(planet);
                        }
                );

    }


}
