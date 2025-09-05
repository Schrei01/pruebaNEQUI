package co.com.bancolombia.mongo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import co.com.bancolombia.model.franchise.gateways.FranchiseMongoRepository;

@Component
@RequiredArgsConstructor
public class FranchiseMongoAdapter implements FranchiseMongoRepository{

    private final FranchiseMongoRepository repository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(franchise);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Franchise> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
