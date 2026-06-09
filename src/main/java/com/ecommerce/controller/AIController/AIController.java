package com.ecommerce.controller.AIController;

import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.service.AIService.RecommendationService;
@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final RecommendationService recommendationService;

    public AIController(
            RecommendationService recommendationService) {

        this.recommendationService = recommendationService;
    }

    @GetMapping("/search")
    public List<Document> search(
            @RequestParam String query) {

        return recommendationService.search(query);
    }

    @GetMapping("/recommend")
    public String recommend(
            @RequestParam String query) {

        return recommendationService.recommend(query);
    }
}