package com.java10x.MagicFridgeAI.controller;

import com.java10x.MagicFridgeAI.model.FoodItem;
import com.java10x.MagicFridgeAI.service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private FoodItemService service;

    public FoodItemController(FoodItemService foodItemService) {
        this.service = foodItemService;
    }

    //POST
    @PostMapping("/criar")
    public ResponseEntity<FoodItem> criar(@RequestBody FoodItem foodItem){
        FoodItem salvo = service.salvar(foodItem);
        return ResponseEntity.ok(salvo);
    }

    //GET
    @GetMapping("/listar")
    public ResponseEntity<List<FoodItem>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<FoodItem> listarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.listarPorId(id));
    }

    //UPDATE
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<FoodItem> alterar(@PathVariable Long id, @RequestBody FoodItem foodItem){
        FoodItem comida = service.alterar(id, foodItem);
        return ResponseEntity.ok(comida);
    }

    //DELETE
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.ok("Alimento com id: " + id + " deletado com sucesso!");
    }
}
