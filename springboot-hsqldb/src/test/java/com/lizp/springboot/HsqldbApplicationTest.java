package com.lizp.springboot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.lizp.springboot.dao.PersonDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HsqldbApplicationTest {
	private static final Logger log = LoggerFactory.getLogger(HsqldbApplicationTest.class);
	private MockMvc mvc;
	@Autowired
	private PersonDao personDao;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
	}

	@Test
	public void test1() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/index").accept(MediaType.APPLICATION_JSON_UTF8).param("id", "3"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void test2() throws Exception {
		// Optional<Person> optional = personDao.findById(2L);
		log.info("{}", personDao.count());
		log.info("{}", personDao.findAll());
	}

	@Test
	public void test3() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/save").accept(MediaType.APPLICATION_JSON_UTF8).param("age", "11")
				.param("name", "haha")).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

}
