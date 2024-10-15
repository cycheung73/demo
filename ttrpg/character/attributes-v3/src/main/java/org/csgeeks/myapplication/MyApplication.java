package org.csgeeks.myapplication;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;


// import org.apache.logging.log4j.Logger;
// import org.apache.logging.log4j.LogManager;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;

// import org.csgeeks.myapplication.model.PointBuyChoice;
// import org.csgeeks.myapplication.model.PointBuyChoiceRepository;


// @Controller
@SpringBootApplication
public class MyApplication {

	// private static final Logger log = LogManager.getLogger(MyApplication.class);

	// @GetMapping("/greeting")
	// public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
	// 	model.addAttribute("name", name);
	// 	return "greeting";
	// }


	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}


	// @Bean
	// public CommandLineRunner demo(PointBuyChoiceRepository repository) {
	// 	return (args) -> {
	// 		// fetch all Point Buy Choices
	// 		log.info("Point Buy Choices found with findAll():");
	// 		log.info("=======================================");
	// 		repository.findAll().forEach(choice -> {
	// 				log.info(choice.toString());
	// 			});
	// 		log.info("");
			
	// 		// fetch Point Buy Choice: 20
	// 		Optional<PointBuyChoice> choice20 = repository.findById(20);
	// 		log.info("Standard Array found with findById(20):");
	// 		log.info("=======================================");
	// 		log.info(choice20.orElse(PointBuyChoice.NULL_CHOICE).toString());
	// 		log.info("");

	// 		// fetch Point Buy Choice: 20
	// 		log.info("Standard Array with   findByIdList(20):");
	// 		log.info("=======================================");
	// 		repository.findByIdList(20).forEach(choice -> {
	// 				log.info(choice.toString());
	// 			});
	// 		log.info("");
			
	// 		// fetch Standard Array
	// 		log.info("Standard Array w/  findStandardArray():");
	// 		log.info("=======================================");
	// 		repository.findStandardArray().forEach(choice -> {
	// 				log.info(choice.toString());
	// 			});
	// 		log.info("");
			
	// 		// fetch Point Buy Choices by Attribute1
	// 		log.info("Point Buy Choices findByAttribute1(15):");
	// 		log.info("=======================================");
	// 		repository.findByAttribute1(15).forEach(choice -> {
	// 				log.info(choice.toString());
	// 			});
	// 		log.info("");
			
	// 	};
	// }
	
}
