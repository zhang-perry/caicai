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

import com.voai.caicai.util.CaicaiFileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Perry Zhang
 * @Version 1.0
 */
@SpringBootTest
public class CaicaiFileTypeTest {


    @Value("classpath:bikes.json")
    private Resource jsonResource;

    @Value("classpath:text_source.txt")
    private Resource textResource;

    @Value("classpath:sample1.pdf")
    private Resource pdfResource;

    @Value("classpath:word-sample.docx")
    private Resource wordResource;

    @Test
    void fileMimeTypeTest() throws IOException {
        MultipartFile file = new MockMultipartFile(textResource.getFilename(), textResource.getInputStream());
        System.out.println(CaicaiFileUtil.getMimeType(file));

    }


}
