package be.hackinthewoods.childfocus.backend;

import be.hackinthewoods.childfocus.backend.entity.Coordinate;
import be.hackinthewoods.childfocus.backend.entity.DisplayLocation;
import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.repository.PosterRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PosterScenarii extends AbstractEndToEndTest {

    private Poster poster;
    private DisplayLocation auPainQuotidien;
    private DisplayLocation carglass;
    @Autowired
    private PosterRepository posterRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void beforeEach() {
        super.beforeEach();

        auPainQuotidien = new DisplayLocation();
        auPainQuotidien.setCoordinate(new Coordinate(BigDecimal.valueOf(12.345678), BigDecimal.valueOf(98.765432)));

        carglass = new DisplayLocation();
        carglass.setCoordinate(new Coordinate(BigDecimal.valueOf(23.456789), BigDecimal.valueOf(87.654321)));

        poster = new Poster();
        poster.addDisplayLocation(auPainQuotidien);
        poster.addDisplayLocation(carglass);

        posterRepository.save(poster);
    }

    @Test
    @Transactional
    public void putPoster() throws Exception {
        String token = login();

        Poster updatedPoster = new Poster();
        updatedPoster.setId(poster.getId());
        updatedPoster.addDisplayLocation(auPainQuotidien);
        updatedPoster.addDisplayLocation(carglass);

        DisplayLocation nightAndDay = new DisplayLocation();
        nightAndDay.setCoordinate(new Coordinate(BigDecimal.valueOf(13.245678), BigDecimal.valueOf(97.865432)));
        updatedPoster.addDisplayLocation(nightAndDay);

        updatePoster(token, updatedPoster);
        verifyPoster(updatedPoster);
    }

    @Test
    @Transactional
    public void removePoster() throws Exception {
        String token = login();

        Poster updatedPoster = new Poster();
        updatedPoster.setId(poster.getId());
        updatedPoster.addDisplayLocation(auPainQuotidien);

        updatePoster(token, updatedPoster);
        verifyPoster(updatedPoster);
    }

    private void updatePoster(String token, Poster updatedPoster) throws Exception {
        String posterJson = mapper.writeValueAsString(updatedPoster);

        mockMvc.perform(post("/api/posters")
          .header(HttpHeaders.AUTHORIZATION, token)
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(posterJson)
        )
          .andExpect(status().isOk());
    }

    private void verifyPoster(Poster updatedPoster) {
        Poster savedPoster = posterRepository.findById(updatedPoster.getId()).get();
        assertThat(savedPoster.getId()).isEqualTo(updatedPoster.getId());
        assertThat(savedPoster.getDisplayLocations())
          .extracting(DisplayLocation::getCoordinate)
          .usingElementComparatorIgnoringFields("id")
          .containsExactlyElementsOf(updatedPoster.getDisplayLocations().stream().map(DisplayLocation::getCoordinate).collect(Collectors.toList()));
    }
}
