package com.java10x.MagicFridgeAI.controller;

import com.java10x.MagicFridgeAI.model.FoodItem;
import com.java10x.MagicFridgeAI.service.ChatGptService;
import com.java10x.MagicFridgeAI.service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RecipeController {

    private FoodItemService foodService;

    private ChatGptService service;

    public RecipeController(FoodItemService foodService, ChatGptService service) {
        this.foodService = foodService;
        this.service = service;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe(){
        List<FoodItem> foodItems = foodService.listar();
        return service.generateRecipe(foodItems)
                .map(recipe -> ResponseEntity.ok(recipe))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

}
