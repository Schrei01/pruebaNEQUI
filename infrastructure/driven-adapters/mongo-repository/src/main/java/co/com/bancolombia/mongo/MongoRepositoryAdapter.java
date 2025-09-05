package co.com.bancolombia.mongo;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MongoRepositoryAdapter extends AdapterOperations<Franchise, Franchise, String, MongoDBRepository>
        implements FranchiseRepository {

    public MongoRepositoryAdapter(MongoDBRepository repository, ObjectMapper mapper) {
        // Si tu modelo de dominio es igual al del adaptador, puedes mapear directamente así:
        super(repository, mapper, d -> mapper.map(d, Franchise.class));
    }
}
