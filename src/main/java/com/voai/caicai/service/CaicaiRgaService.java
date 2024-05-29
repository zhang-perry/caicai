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

package com.voai.caicai.service;

import com.voai.caicai.util.CaicaiFileUtil;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Perry Zhang
 * @Version 1.0
 */
@Service
public class CaicaiRgaService {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private VectorStore vectorStore;

    @Value("${spring.servlet.multipart.location}")
    private String mediaLocation;

    @Value("${caicai.media.path}")
    private String mediaPath;

    public void upload(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        File file = null;
        try {
            file = ResourceUtils.getFile(mediaPath + fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found!");
        }
        if (file.exists()) {
            throw new RuntimeException("File already exist!");
        }
        Resource resource = multipartFile.getResource();
        String mimeType = CaicaiFileUtil.getMimeType(multipartFile);
        List<Document> transformedDocuments = null;
        if (mimeType.equals("text/plain")) {
            TextReader textReader = new TextReader(resource);
            textReader.getCustomMetadata().put("FILE_NAME", fileName);
            /*List<Document> documents = new ArrayList<>();
            documents.addAll(textReader.get());*/
            List<Document> documents = textReader.read();
            var textSplitter = new TokenTextSplitter();
            transformedDocuments = textSplitter.apply(documents);
        } else if (mimeType.equals("application/pdf")) {
            //TODO: Implement PDF reader
        } else if (mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            //TODO: Implement Excel reader
        } else if (mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            //TODO: Implement Word reader
        } else {
            throw new RuntimeException("系统支持txt、pdf、docx、json格式文件");
        }
        if (!transformedDocuments.isEmpty()) {
            vectorStore.add(transformedDocuments);
        }
        try {
            multipartFile.transferTo(new File(mediaPath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Document> search(String keyword) {
        return vectorStore.similaritySearch(SearchRequest.query(keyword).withTopK(5));
    }

    public String chat(String message) {
        List<Document> similarDocuments = search(message);
        String content = similarDocuments.stream()
                .map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));
        String chatResponse = chatModel.call(chatPrompt2String(message, content));
        return chatResponse;
    }

    private String chatPrompt2String(String message, String content) {
        String promptText = """
                You are a helpful AI assistant that helps people find information."%s":
                %s
                """;
        return String.format(promptText, message, content);
    }

}
