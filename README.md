# Caicai Spring AI Exploring Project Getting Started

**ACAI**

A [Spring AI](https://spring.io/projects/spring-ai) Exploring Project[Caicai]. Functions include:

1) Support Ollama Chat, using the Spring AI Chat SDK.
2) Support RAG(Retrieval Augmented Generation), able to retrieve and generate content based on user queries and input
   documents.
3) TESTS: Embedding, Vector Store, ETL pipeline

TODO:

1) Image
2) Audio
3) Function Calling
4) Multimodality
5) User Interface
6) Evaluation Testing

## 1. Project preparation

### Basic development environment preparation

| Environment | Version                |
|------------:|------------------------|
|        java | OpenJDK version 21.0.1 |
|      gradle | Gradle 8.6             |
|    pgvector | pgvector:pg16          |
|      ollama | 0.1.38                 |
|      httpie | 3.2.2                  |

## 2. Running Models

### 2.1 for chat

```shell
ollama run llama3
```

OR

```shell
ollama run wangshenzhi/llama3-8b-chinese-chat-ollama-q8:latest
```

#### 2.2 for text embedding

```shell
ollama rum nomic-embed-text
```

## 3. Running the Spring Boot Application

```shell
./gradlew bootRun
```

## 4、Retrieval Augmented Generation([RAG](https://docs.spring.io/spring-ai/reference/concepts.html#concept-rag))

### 4.1 Uploading documents

```shell
http -f POST :8080/rga/upload file@~/Desktop/upload_test.txt 
```

### 4.2 Searching documents

```shell
http :8080/rga/search keyword=="Caicai AI test"
```

### 4.3 Chatting with documents

```shell
http :8080/rga/chat message=="Caicai AI test"
```

## 5. Chat(Default)

```shell
http :8080/chat message=="Tell me a funny story."
```

```shell
http :8080/chat/generic-options message=="what is spring AI? Give a short answer."
```

```shell
http :8080/chat/generic-options message=="what is spring AI? Give a short answer."
```

## 6. API Document

> API DOC: http://localhost:8080/swagger-ui/index.html

