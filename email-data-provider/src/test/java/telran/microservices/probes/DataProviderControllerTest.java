package telran.microservices.probes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.microservices.probes.dto.EmailDate;
import telran.microservices.probes.services.DataProvider;

@WebMvcTest
class DataProviderControllerTest {
private static final long SENSOR_ID_NORMAL = 123L;
private static final long SENSOR_ID_NOT_EXIST = 124L;
private static final String ERROR_MESSAGE = "Error. Sensor ID does not exist" + SENSOR_ID_NOT_EXIST;
@Autowired
MockMvc mockMvc;
@MockBean
DataProvider dataProvider;
@Autowired
ObjectMapper objectMapper;
EmailDate emailData = new EmailDate(new String[] {"vasya@gmail.com", "petya@gmail.com"},
		new String[] {"vasya", "petya"});
	@Test
	void dataExistTest() throws Exception{
		when(dataProvider.getEmailDate(SENSOR_ID_NORMAL)).thenReturn(emailData);
		String jsonEmailDtat = mockMvc.perform(get("http://localhost:8080/email/data/" + SENSOR_ID_NORMAL))
		.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		String jsonExpeceted = objectMapper.writeValueAsString(emailData);
		assertEquals(jsonExpeceted, jsonEmailDtat);
	}
	
	@Test
	void dataNotExistTest() throws Exception{
		when(dataProvider.getEmailDate(SENSOR_ID_NOT_EXIST)).thenThrow(new IllegalArgumentException(ERROR_MESSAGE));
		String response = mockMvc.perform(get("http://localhost:8080/email/data/" + SENSOR_ID_NOT_EXIST))
				.andDo(print()).andExpect(status().isNotFound()).andReturn()
				.getResponse().getContentAsString();
		assertEquals(ERROR_MESSAGE, response);
	}

}