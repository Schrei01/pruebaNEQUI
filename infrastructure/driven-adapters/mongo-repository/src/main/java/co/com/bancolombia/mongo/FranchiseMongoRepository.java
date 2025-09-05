package co.com.bancolombia.mongo;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FranchiseMongoRepository extends ReactiveMongoRepository<Franchise, String> {
}
