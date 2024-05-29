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
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author Perry Zhang
 * @Version 1.0
 */
@SpringBootTest
public class CaicaiETLPipelineTest {

    @Value("classpath:bikes.json")
    private Resource jsonResource;

    @Value("classpath:text_source.txt")
    private Resource textResource;

    @Value("classpath:sample1.pdf")
    private Resource pdfResource;

    @Value("classpath:word-sample.docx")
    private Resource wordResource;

    @Test
    void jsonReaderTest() {
        JsonReader jsonReader = new JsonReader(jsonResource, "description");
        List<Document> docs = jsonReader.get();

        for (Document doc : docs) {
            System.out.println(doc.toString());
        }

    }

    @Test
    void textReaderTest() {
        TextReader textReader = new TextReader(textResource);
        textReader.getCustomMetadata().put("filename", "text-source.txt");
        List<Document> docs = textReader.get();
        for (Document doc : docs) {
            System.out.println(doc.toString());
        }
    }

    @Test
    void pdfReaderTest() {
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource,
                PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0)
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfTopTextLinesToDelete(0)
                                .build())
                        .withPagesPerDocument(1)
                        .build());

        List<Document> docs = pdfReader.get();

        for (Document doc : docs) {
            System.out.println(doc.toString());
        }
    }

    @Test
    void tikaDocReaderTest() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(wordResource);
        List<Document> docs = tikaDocumentReader.get();

        for (Document doc : docs) {
            System.out.println(doc.toString());
        }
    }

}
