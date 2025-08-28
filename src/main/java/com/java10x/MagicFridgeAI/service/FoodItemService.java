package com.java10x.MagicFridgeAI.service;

import com.java10x.MagicFridgeAI.model.FoodItem;
import com.java10x.MagicFridgeAI.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {

    private FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) {
        this.repository = repository;
    }

    public FoodItem salvar(FoodItem foodItem){
        return repository.save(foodItem);
    }

    public List<FoodItem> listar(){
        return repository.findAll();
    }

    public Optional<FoodItem> listarPorId(Long id){
        return repository.findById(id);
    }

    public FoodItem alterar(Long id, FoodItem foodItem){
        if (repository.existsById(id)){
            foodItem.setId(id);
            return repository.save(foodItem);
        }
        return null;
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }
}
