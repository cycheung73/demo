package org.csgeeks.myapplication;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TestRestTemplate template;


	@Test
	public void getIndex() throws Exception {
	    ResponseEntity<String> response = template.getForEntity("/", String.class);
	    assertThat(response.getBody())
		.isEqualTo("<!DOCTYPE HTML>\n<html>\n  <head>\n    <title>Main Menu</title>\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n  </head>\n\n  <body>\n    <p><a href=\"/standard\">Standard Array</a></p>\n    <p><a href=\"/pointbuy\">Point Buy</a></p>\n    <p><a href=\"/random\">4d6 Drop Lowest</a></p>\n  </body>\n</html>\n");
	}


	// @Test
	// public void getGreeting() throws Exception {
	//     ResponseEntity<String> response = template.getForEntity("/greeting", String.class);
	//     assertThat(response.getBody())
	// 	.isEqualTo("<!DOCTYPE HTML>\n<html>\n  <head>\n    <title>Getting Started: Serving Web Content</title>\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n  </head>\n\n  <body>\n    <p >Hello, World!</p>\n  </body>\n</html>\n");
	// }

}
