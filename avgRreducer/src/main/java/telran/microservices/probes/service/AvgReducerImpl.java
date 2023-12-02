package telran.microservices.probes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.microservices.probes.dto.Probe;
import telran.microservices.probes.entities.ListProbesValues;
import telran.microservices.probes.repo.ListProbesRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvgReducerImpl implements AvgReducer{
	final ListProbesRepo listProbeRepo;
	@Value("${app.reducing.size:100}")
	int reducingSize;
	

	@Override
	public Integer avgReduce(Probe probe) {
		long id = probe.id();
		Integer res = null;
		ListProbesValues listProbeValues = listProbeRepo.findById(id).orElse(null);
		List<Integer> values = null; 
		if(listProbeValues == null) {
			log.debug("sensor {} not found in Redis", id);
			listProbeValues = new ListProbesValues(id);
		}
		values = listProbeValues.getValues();
		values.add(probe.value());
		if(values.size() == reducingSize) {
			res = values.stream().collect(Collectors.averagingInt(v -> v)).intValue();
			values.clear();
			log.debug("computed average value {} for sensor {}", res, id );
		} else {
			log.trace("no avg value for sensor {}", id);
		}
			
		listProbeRepo.save(listProbeValues);
		log.trace("Redis updated for sensor {}", id);
		return res;
	}

	
}
