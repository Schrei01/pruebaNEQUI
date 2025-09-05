package co.com.bancolombia.usecase;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class FranchiseUseCase {

    private final FranchiseRepository franchiseRepository;


    public Mono<Franchise> addFranchise(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }


    public Mono<Franchise> addBranch(String franchiseId, Branch branch) {
        return franchiseRepository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.getBranches().add(branch);
                    return franchiseRepository.save(franchise);
                });
    }


    public Mono<Franchise> addProduct(String franchiseId, String branchName, Product product) {
        return franchiseRepository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.getBranches().stream()
                            .filter(b -> b.getName().equals(branchName))
                            .findFirst()
                            .ifPresent(branch -> branch.getProducts().add(product));
                    return franchiseRepository.save(franchise);
                });
    }


    public Mono<Franchise> deleteProduct(String franchiseId, String branchName, String productName) {
        return franchiseRepository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.getBranches().stream()
                            .filter(b -> b.getName().equals(branchName))
                            .findFirst()
                            .ifPresent(branch -> branch.getProducts()
                                    .removeIf(product -> product.getName().equals(productName)));
                    return franchiseRepository.save(franchise);
                });
    }


    public Mono<Franchise> updateProductStock(String franchiseId, String branchName, String productName, int newStock) {
        return franchiseRepository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.getBranches().stream()
                            .filter(b -> b.getName().equals(branchName))
                            .findFirst()
                            .ifPresent(branch -> branch.getProducts().stream()
                                    .filter(product -> product.getName().equals(productName))
                                    .findFirst()
                                    .ifPresent(product -> product.setStock(newStock)));
                    return franchiseRepository.save(franchise);
                });
    }


    public Flux<Product> getTopProductsByBranch(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .flatMapMany(franchise -> Flux.fromIterable(franchise.getBranches())
                        .map(branch -> branch.getProducts().stream()
                                .max(Comparator.comparingInt(Product::getStock))
                                .orElse(null))
                        .filter(Objects::nonNull));
    }
}
