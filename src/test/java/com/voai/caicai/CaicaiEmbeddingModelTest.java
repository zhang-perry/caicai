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
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Perry Zhang
 * @Version 1.0
 */
@SpringBootTest
public class CaicaiEmbeddingModelTest {
    @Autowired
    private OllamaChatModel chatModel;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Test
    void embeddingOptionsTest() {
        System.out.println("caicai embedding options: " + chatModel.getDefaultOptions().toString());
    }

    @Test
    void embeddingModelCallTest() {
        EmbeddingResponse embeddingResponse = embeddingModel.call(
                new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"),
                        OllamaOptions.create()
                                .withModel("nomic-embed-text")));
        for (Embedding result : embeddingResponse.getResults()) {
            System.out.println(Arrays.toString(result.getOutput().toArray()));
        }
    }

    @Test
    void embeddingModelResponseTest() {
        EmbeddingResponse embeddingResponse = embeddingModel.embedForResponse(List.of("Hello World", "World is big and salvation is near"));
        for (Embedding result : embeddingResponse.getResults()) {
            System.out.println(Arrays.toString(result.getOutput().toArray()));
            System.out.println(Map.of("embedding", embeddingResponse).toString());
        }
    }
}
