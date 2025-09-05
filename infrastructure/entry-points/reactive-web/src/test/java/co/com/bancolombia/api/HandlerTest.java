package co.com.bancolombia.api;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.usecase.FranchiseUseCase;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest
@Import({Handler.class, RouterRest.class})
class HandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private FranchiseUseCase franchiseUseCase;

    private Franchise sampleFranchise;

    @BeforeEach
    void setUp() {
        sampleFranchise = Franchise.builder()
                .id("1")
                .name("My Franchise")
                .build();
    }

    @Test
    void shouldAddFranchiseSuccessfully() {
        Mockito.when(franchiseUseCase.addFranchise(Mockito.any(Franchise.class)))
                .thenReturn(Mono.just(sampleFranchise));

        webTestClient.post()
                .uri("/api/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(sampleFranchise)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("My Franchise")
                .jsonPath("$.id").isEqualTo("1");
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public FranchiseUseCase franchiseUseCase() {
            return Mockito.mock(FranchiseUseCase.class);
        }
    }
}
