/*
 * Copyright (c) 2024 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.voai.caicai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author Perry Zhang
 * @Version 1.0
 */
@SpringBootTest
public class CaicaiPgvectorStoreTest {

    @Autowired
    private VectorStore vectorStore;

    @Test
    public void testPgvector() {
        // Add documents to the vector store
        List<Document> documents = List.of(
                new Document("Caicai is coming! Caicai is coming! Caicai is coming! Caicai is coming!", Map.of("meta1", "meta1")),
                new Document("Each Spring project has its own; it explains in great details how you can use project features and what you can achieve with them."),
                new Document("Spring AI is an application framework for AI engineering. Its goal is to apply to the AI domain Spring ecosystem design principles such as portability and modular design and promote using POJOs as the building blocks of an application to the AI domain.", Map.of("meta2", "meta2")));
        vectorStore.add(documents);

        // Perform a similarity search
        List<Document> results = vectorStore.similaritySearch(SearchRequest.query("Caicai").withTopK(5));

        for (Document result : results) {
            System.out.println(result);
        }
    }
}
