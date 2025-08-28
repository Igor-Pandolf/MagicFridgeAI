package com.java10x.MagicFridgeAI.service;

import com.java10x.MagicFridgeAI.model.FoodItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Dica: Renomeie esta classe para GeminiService para ficar mais claro
@Service
public class ChatGptService {

    private final WebClient webClient;

    // A inicialização acontece AQUI, dentro do construtor
    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(List<FoodItem> foodItems){

        String alimentos = foodItems.stream()
                .map(item -> String.format("%s (%s) - Quantidade: %d, Validade: %s",
                        item.getNome(),
                        item.getCategoria(),
                        item.getQuantidade(),
                        item.getValidade()))
                .collect(Collectors.joining("\n"));

        // O resto do seu código está perfeito e não precisa de alterações.
        String instruction = "Você é um assistente de culinária que cria receitas fáceis e com um toque divertido.";
        String prompt = "Baseado no meu banco de dados faça uma receita com os seguintes itens: " + alimentos;

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", instruction
                        ),
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );
        return webClient.post()
                .uri("/chat/completions") // Adicionar o URI aqui é uma boa prática
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var choices = (List<Map<String, Object>>) response.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return message.get("content").toString();
                    }
                    return "Nenhuma receita foi gerada.";
                });
    }
}