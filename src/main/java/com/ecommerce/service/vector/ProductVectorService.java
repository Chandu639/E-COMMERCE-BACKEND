package com.ecommerce.service.vector;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.exception.product.ProductNotFoundException;
import com.ecommerce.model.ProductModel.ProductEntity;
import com.ecommerce.repo.ProductRepo.ProductRepo;
import com.ecommerce.service.AIService.ProductEmbeddingService;

@Service
public class ProductVectorService {

    private static final Logger log =
            LoggerFactory.getLogger(ProductVectorService.class);

    private final ProductRepo productRepo;
    private final ProductEmbeddingService productEmbeddingService;
    private final VectorStore vectorStore;
    private final JdbcTemplate pgJdbcTemplate;

    public ProductVectorService(
            ProductRepo productRepo,
            ProductEmbeddingService productEmbeddingService,
            VectorStore vectorStore,
            @Qualifier("pgJdbcTemplate")
            JdbcTemplate pgJdbcTemplate) {

        this.productRepo = productRepo;
        this.productEmbeddingService = productEmbeddingService;
        this.vectorStore = vectorStore;
        this.pgJdbcTemplate = pgJdbcTemplate;
    }

    public void vectorizeProduct(int productId) {

        ProductEntity product =
                productRepo.findById(productId)
                        .orElseThrow(ProductNotFoundException::new);

        log.info("Vectorizing product. productId={}, productName={}",
                productId,
                product.getProductName());

        Document document =
                productEmbeddingService.toDocument(product);

        vectorStore.add(List.of(document));

        log.info("Product vectorized successfully. productId={}",
                productId);
    }

    @Transactional
    public void reVectorizeProduct(int productId) {

        log.info("Re-vectorizing product. productId={}",
                productId);

        deleteVector(productId);

        vectorizeProduct(productId);
    }

    public void deleteVector(int productId) {

        int rowsDeleted =
                pgJdbcTemplate.update(
                        """
                        DELETE FROM vector_store
                        WHERE metadata->>'productId' = ?
                        """,
                        String.valueOf(productId)
                );

        log.info(
                "Deleted {} vector record(s) for productId={}",
                rowsDeleted,
                productId
        );
    }
}