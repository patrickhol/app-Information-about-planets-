package devlab.mappers;

import devlab.commons.Mapper;
import devlab.models.Planet;
import devlab.models.Tag;
import devlab.models.dtos.PlanetDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PlanetMapper implements Mapper<Planet, PlanetDto> {


    @Override
    public PlanetDto map(Planet from) {
        List<String> tags = from
                .getTags()
                .stream()
                .map(TagsToStringsList.INSTANCE)
                .collect(Collectors.toList());


        return PlanetDto.builder()
                .planetName(from.getPlanetName())
                .distanceFromSun(from.getDistanceFromSun())
                .oneWayLightTimeToTheSun(from.getOneWayLightTimeToTheSun())
                .lengthOfYear(from.getLengthOfYear())
                .planetType(from.getPlanetType())
                .planetInfo(from.getPlanetInfo())
                .planetImage(from.getPlanetImage())
                .tags(tags)
                .build();
    }

    @Override
    public Planet reverse(PlanetDto to) {


        return Planet
                .builder()
                .planetName(to.getPlanetName())
                .distanceFromSun(to.getDistanceFromSun())
                .oneWayLightTimeToTheSun(to.getOneWayLightTimeToTheSun())
                .lengthOfYear(to.getLengthOfYear())
                .planetType(to.getPlanetType())
                .planetInfo(to.getPlanetInfo())
                .planetImage(to.getPlanetImage())
                .build();

    }

    private enum TagsToStringsList implements Function<Tag, String> {
        INSTANCE;

        @Override
        public String apply(Tag tag) {
            return tag.getTitle();
        }


    }
}
