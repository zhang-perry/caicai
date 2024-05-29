
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

import com.voai.caicai.service.CaicaiRgaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Perry Zhang
 * @Version 1.0
 */
@RestController
@RequestMapping("/rga")
@Tag(name = "Caicai Rga", description = "Caicai Rga Demo")
public class CaicaiRgaController {
    @Autowired
    private CaicaiRgaService caicaiRgaService;

    @Operation(summary = "Upload Document")
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile) {
        caicaiRgaService.upload(multipartFile);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "Search Document")
    @GetMapping("/search")
    public ResponseEntity<List<Document>> searchDoc(@RequestParam String keyword) {
        return ResponseEntity.ok(caicaiRgaService.search(keyword));
    }

    @Operation(summary = "Chat with Document Retriever")
    @GetMapping("/chat")
    public ResponseEntity chat(@RequestParam String message) {
        return ResponseEntity.ok(caicaiRgaService.chat(message));
    }

}
