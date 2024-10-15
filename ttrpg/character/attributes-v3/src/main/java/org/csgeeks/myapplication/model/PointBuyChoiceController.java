package org.csgeeks.myapplication.model;

import org.csgeeks.myapplication.Dice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class PointBuyChoiceController {

	private final PointBuyChoiceRepository choiceRepository;

	public PointBuyChoiceController(PointBuyChoiceRepository choiceRepository) {
		this.choiceRepository = choiceRepository;
	}

	@GetMapping("/pointbuy")
	public String pointbuy(@RequestParam(name="attribute1", required=false) String attribute1, @RequestParam(name="attribute2", required=false) String attribute2, Model model) {
		List<PointBuyChoice> choices;
		try {
			Integer attrib2 = Integer.parseInt(attribute2);
			try {
				Integer attrib1 = Integer.parseInt(attribute1);
				choices = choiceRepository.findByAttribute1AndAttribute2(attrib1, attrib2);
			} catch (Exception ex2) {
				choices = choiceRepository.findByAttribute2AndAttribute1Is15(attrib2);
			}
		} catch (Exception ex1) {
			try {
				Integer attrib1 = Integer.parseInt(attribute1);
				choices = choiceRepository.findByAttribute1(attrib1);
			} catch (Exception ex3) {
				choices = choiceRepository.findAll();
			}
		}
		model.addAttribute("style", "pointbuy");
		model.addAttribute("choices", choices);
		return "choices";
	}

	@GetMapping("/standard")
	public String standard(Model model) {
		List<PointBuyChoice> choices = choiceRepository.findStandardArray();
		model.addAttribute("style", "standard");
		model.addAttribute("choices", choices);
		return "choices";
	}


	@GetMapping("/random")
	public String random(Model model) {
		List<PointBuyChoice> choices = new ArrayList<PointBuyChoice>();
		int[] rolls = getRandomRolls();
		choices.add(choiceRepository.save(new PointBuyChoice(rolls[0], rolls[1], rolls[2], rolls[3], rolls[4], rolls[5]).setId(66)));
		model.addAttribute("style", "random");
		model.addAttribute("choices", choices);
		return "choices";
	}

	protected int[] getRandomRolls() {
		int[] array = new int[6];
		for (int i = 0; i < 6; i++) {
			array[i] = fourRollsDropLowest();
		}
		return array;
	}

	protected int fourRollsDropLowest() {
		int[] fourRolls = {Dice.D6.roll(), Dice.D6.roll(), Dice.D6.roll(), Dice.D6.roll()};
		Arrays.sort(fourRolls);
		return fourRolls[1] + fourRolls[2] + fourRolls[3];  // exclude fourRolls[0] (lowest)
	}
	
}
