package com.exemplo;

import com.exemplo.api.v1.ExemploRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExemploApplicationTests {

	@Autowired
	private ExemploRestController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
