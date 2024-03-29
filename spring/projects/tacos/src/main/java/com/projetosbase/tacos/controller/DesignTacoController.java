package com.projetosbase.tacos.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.projetosbase.tacos.data.IngredientRepository;
import com.projetosbase.tacos.model.Ingredient;
import com.projetosbase.tacos.model.Taco;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));

		Ingredient.Type[] types = Ingredient.Type.values();

		for (Ingredient.Type type : types) {
			model.addAttribute(
				type.toString().toLowerCase(),
				filterByType(ingredients, type)
			);
		}
		model.addAttribute("design", new Taco());

		return "design";
	}

	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors) {
		if (errors.hasErrors()) {
			return "design";
		}
		// Save the taco design...
		// We'll do this in chapter 3
		log.info("Processing design: " + design);
		return "redirect:/orders/current";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream()
                .filter(ingredient -> type.equals(ingredient.getType()))
                .collect(Collectors.toList());
    }
}
