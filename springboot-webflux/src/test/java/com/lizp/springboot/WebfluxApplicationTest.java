package com.lizp.springboot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebfluxApplicationTest {
	// private static final Logger logger =
	// LoggerFactory.getLogger(WebfluxApplicationTest.class);
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
	}

	@Test
	public void test1() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/index").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void test2() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/person/11").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void test3() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/person").accept(MediaType.APPLICATION_JSON_UTF8).param("age", "11")
				.param("name", "haha")).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

}
