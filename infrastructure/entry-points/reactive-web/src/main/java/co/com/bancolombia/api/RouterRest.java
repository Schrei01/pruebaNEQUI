package co.com.bancolombia.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routes(FranchiseHandler handler) {
        return route(POST("/api/franchises"), handler::addFranchise)
                .andRoute(POST("/api/franchises/{franchiseId}/branches"), handler::addBranch)
                .andRoute(POST("/api/franchises/{franchiseId}/branches/{branchName}/products"), handler::addProduct)
                .andRoute(DELETE("/api/franchises/{franchiseId}/branches/{branchName}/products/{productName}"), handler::deleteProduct)
                .andRoute(PUT("/api/franchises/{franchiseId}/branches/{branchName}/products/{productName}/stock/{newStock}"), handler::updateProductStock)
                .andRoute(GET("/api/franchises/{franchiseId}/branches/top-products"), handler::getTopProductsByBranch);
    }
}

