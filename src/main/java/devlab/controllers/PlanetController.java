package devlab.controllers;


import devlab.commons.annotations.ApiVersionPrefix;
import devlab.models.Planet;
import devlab.models.dtos.PlanetDto;
import devlab.services.PlanetService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@CrossOrigin
@RestController
@ApiVersionPrefix
public class PlanetController {
    private PlanetService planetService;


    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping("/planets")
    public List<PlanetDto> getPlanets(@RequestParam(value = "distance", required = false) Long distance) {

        if (distance != null && distance > 0) {
            return planetService.getPlanetDto(distance);
        }
        return planetService.getPlanetDto();
    }

    @PostMapping("/planets")
    public Planet addPlanet(@RequestBody PlanetDto planetDto) {
        return planetService.addPlanet(planetDto);

    }

    @DeleteMapping("/planets/{planetName}")
    public void deletePlanet(@PathVariable String planetName) {
    planetService.deletePlanetByName(planetName);
    }

    @PutMapping("/planets")
    public void updatePlanet(@RequestBody PlanetDto planetDto){
        planetService.updatePlanet(planetDto);
    }


}
