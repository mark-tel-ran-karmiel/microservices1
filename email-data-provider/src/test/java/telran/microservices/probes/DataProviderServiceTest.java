package telran.microservices.probes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.microservices.probes.dto.EmailDate;
import telran.microservices.probes.services.DataProvider;

@SpringBootTest
@Sql(scripts = "db_test_script.sql")
public class DataProviderServiceTest {
	@Autowired
	DataProvider dataProvider;
	EmailDate  expected;
	
	@Test
	void emailDataExistTest() {
		
	}
	
}
