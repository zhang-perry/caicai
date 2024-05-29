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

package com.voai.caicai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Perry Zhang
 * @Version 1.0
 */
@RestController
@Tag(name = "Caicai Chat", description = "Chat with Caicai Demo")
public class CaicaiChatController {
    private final ChatModel chatModel;

    CaicaiChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Operation(summary = "Chat with Caicai", description = "Chat with Caicai using the default options")
    @GetMapping("/chat")
    String chat(@RequestParam(defaultValue = "What is Caicai?") String message) {
        return chatModel.call(message);
    }

    @Operation(summary = "Chat with Caicai using generic options", description = "Chat with Caicai using generic options")
    @GetMapping("/chat/generic-options")
    String chatWithGenericOptions(@RequestParam(defaultValue = "What is Caicai??") String message) {
        return chatModel.call(new Prompt(message, ChatOptionsBuilder.builder()
                        .withTemperature(1.3f)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @Operation(summary = "Chat with Caicai using Ollama options", description = "Chat with Caicai using Ollama options")
    @GetMapping("/chat/ollama-options")
    String chatWithOllamaOptions(@RequestParam(defaultValue = "What is Caicai??") String message) {
        return chatModel.call(new Prompt(message, OllamaOptions.create()
                        .withModel("llama3")
                        .withRepeatPenalty(1.5f)))
                .getResult().getOutput().getContent();
    }
}
