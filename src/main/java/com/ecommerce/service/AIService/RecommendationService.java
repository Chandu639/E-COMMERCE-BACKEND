package com.ecommerce.service.AIService;

import java.util.List;
	import java.util.stream.Collectors;

	import org.springframework.ai.chat.client.ChatClient;
	import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
	import org.springframework.stereotype.Service;

	@Service
	public class RecommendationService {

	    private final VectorStore vectorStore;
	    private final ChatClient chatClient;

	    public RecommendationService(
	            VectorStore vectorStore,
	            ChatClient.Builder chatClientBuilder) {

	        this.vectorStore = vectorStore;
	        this.chatClient = chatClientBuilder.build();
	    }

	    public String recommend(String query) {

	        List<Document> documents =
	                vectorStore.similaritySearch(query);

	        String context =
	                documents.stream()
	                        .map(Document::getText)
	                        .collect(Collectors.joining("\n\n"));

	        String prompt = """
	        		You are an expert ecommerce assistant.

	        		User Requirement:
	        		%s

	        		Available Products:
	        		%s

	        		Instructions:

	        		1. Recommend at most 3 products.
	        		2. Use ONLY the products provided.
	        		3. Do NOT invent products.
	        		4. Keep the response under 250 words.
	        		5. Explain why each product matches the requirement.
	        		6. Mention Product Name, Brand and Price.
	        		7. If no product matches, say so.

	        		Format:

	        		Product Name:
	        		Brand:
	        		Price:
	        		Why it matches:

	        		Finally provide:

	        		BEST OVERALL CHOICE
	        		""".formatted(query, context);

	        return chatClient.prompt()
	                .user(prompt)
	                .call()
	                .content();
	    }
	    
	    public List<Document> search(String query) {

	        return vectorStore.similaritySearch(
	                SearchRequest.builder()
	                        .query(query)
	                        .topK(3)
	                        .build()
	        );
	    }
	}

