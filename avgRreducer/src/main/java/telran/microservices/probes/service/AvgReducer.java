package telran.microservices.probes.service;

import telran.microservices.probes.dto.Probe;

public interface AvgReducer {
	Integer avgReduce(Probe probe);

}
