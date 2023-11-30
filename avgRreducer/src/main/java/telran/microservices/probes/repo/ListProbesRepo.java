package telran.microservices.probes.repo;

import org.springframework.data.repository.CrudRepository;

import telran.microservices.probes.entities.ListProbesValues;

public interface ListProbesRepo extends CrudRepository<ListProbesValues, Long> {
	
}
