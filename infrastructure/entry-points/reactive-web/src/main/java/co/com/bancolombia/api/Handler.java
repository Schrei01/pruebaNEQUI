package co.com.bancolombia.api;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.usecase.FranchiseUseCase;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final FranchiseUseCase franchiseUseCase;

    // 1) Add Franchise
    public Mono<ServerResponse> addFranchise(ServerRequest request) {
        return request.bodyToMono(Franchise.class)
                .flatMap(franchiseUseCase::addFranchise)
                .flatMap(franchise -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(franchise));
    }

    // 2) Add Branch
    public Mono<ServerResponse> addBranch(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return request.bodyToMono(Branch.class)
                .flatMap(branch -> franchiseUseCase.addBranch(franchiseId, branch))
                .flatMap(franchise -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(franchise));
    }

    // 3) Add Product
    public Mono<ServerResponse> addProduct(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchName  = request.pathVariable("branchName");
        return request.bodyToMono(Product.class)
                .flatMap(product -> franchiseUseCase.addProduct(franchiseId, branchName, product))
                .flatMap(franchise -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(franchise));
    }

    // 4) Delete Product
    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchName  = request.pathVariable("branchName");
        String productName = request.pathVariable("productName");
        return franchiseUseCase.deleteProduct(franchiseId, branchName, productName)
                .flatMap(franchise -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(franchise));
    }

    // 5) Update Product Stock
    public Mono<ServerResponse> updateProductStock(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchName  = request.pathVariable("branchName");
        String productName = request.pathVariable("productName");
        int newStock       = Integer.parseInt(request.pathVariable("newStock"));
        return franchiseUseCase.updateProductStock(franchiseId, branchName, productName, newStock)
                .flatMap(franchise -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(franchise));
    }

    // 6) Top product by stock per branch
    public Mono<ServerResponse> getTopProductsByBranch(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        Flux<Product> topProducts = franchiseUseCase.getTopProductsByBranch(franchiseId);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(topProducts, Product.class);
    }
}
