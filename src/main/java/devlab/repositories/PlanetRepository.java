package devlab.repositories;

import devlab.models.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

    @Query("select p from Planet p where p.distanceFromSun <= ?1")
    List<Planet> findPlanetByDistanceFromSun(Long distance);

    @Query("select p from Planet p where p.planetName = ?1")
    Optional<Planet> findPlanetByPlanetName(String name);


    @Transactional
    @Modifying
    @Query("delete from Planet p where p.planetName = ?1")
    void deletetByPlanetName(String name);


}
