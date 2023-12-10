package telran.microservices.probes.services;

import telran.microservices.probes.dto.EmailDate;

public interface DataProvider {
	EmailDate getEmailDate(long id);
	
	

}
