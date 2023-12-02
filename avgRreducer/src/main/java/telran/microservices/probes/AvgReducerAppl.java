package telran.microservices.probes;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.microservices.probes.dto.Probe;
import telran.microservices.probes.service.AvgReducer;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class AvgReducerAppl {
final AvgReducer avgReducer;
final StreamBridge streamBridge;
@Value("${app.avg.producer.binding.name:avgProducer-out-0}")
String bindingName;
	public static void main(String[] args) {
		SpringApplication.run(AvgReducerAppl.class, args);

	}
	@Bean
	Consumer<Probe> avgConsumer() {
		return this::processProbe;
	}
	void processProbe(Probe probe) {
		log.trace("received probe: {}", probe);
		long id = probe.id();
		Integer avgValue = avgReducer.avgReduce(probe);
		if (avgValue != null) {
			Probe avgProbe = new Probe(id,avgValue);
			streamBridge.send(bindingName, avgProbe);
			log.debug("probe {} has been sent to {}", avgProbe, bindingName);
		} 
		
		
	}

}