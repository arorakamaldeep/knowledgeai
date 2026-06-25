# KnowledgeAI

KnowledgeAI is an Enterprise Knowledge Management Platform built using Java and Spring Boot. The project is designed to ingest enterprise documents, extract content, process knowledge, and prepare data for Retrieval-Augmented Generation (RAG) based AI applications.

## Features Implemented

### Document Management

* Upload PDF documents
* Store document metadata in PostgreSQL
* Retrieve uploaded documents
* File storage management

### PDF Processing

* Extract text from PDF documents using Apache PDFBox
* Store extracted text in the database
* Retrieve extracted document content

### Text Chunking

* Split large documents into smaller chunks
* Store chunks for future embedding generation
* Retrieve document chunks

### API Documentation

* Swagger/OpenAPI integration

## Technology Stack

### Backend

* Java 21
* Spring Boot 3.5.x
* Spring Data JPA
* Maven

### Database

* PostgreSQL

### Document Processing

* Apache PDFBox

### Documentation

* Swagger / OpenAPI

## Current Architecture

PDF Upload
→ Store File
→ Extract Text
→ Store Metadata
→ Create Chunks
→ Retrieve Chunks

## REST APIs

### Upload Document

POST /api/v1/documents/upload

### Get Document

GET /api/v1/documents/{id}

### Get Extracted Text

GET /api/v1/documents/{id}/text

### Create Chunks

POST /api/v1/documents/{id}/chunks

### Get Chunks

GET /api/v1/documents/{id}/chunks

## Project Structure

src/main/java

* document

  * controller
  * service
  * repository
  * entity
  * dto
  * util

* chunk

  * controller
  * service
  * repository
  * entity
  * dto
  * util

## Upcoming Features

### Phase 2 – GenAI Integration

* OpenAI Integration
* Embedding Generation
* Vector Storage using pgvector
* Semantic Search

### Phase 3 – RAG

* Context Retrieval
* AI Chat Interface
* Knowledge Assistant

### Phase 4 – Enterprise Features

* JWT Authentication
* Role-Based Access Control
* Multi-user Support
* Audit Logging

## Learning Goals

This project is being built to gain hands-on experience with:

* Spring AI
* Large Language Models (LLMs)
* Retrieval-Augmented Generation (RAG)
* Vector Databases
* Enterprise AI System Design

## Author

Developed as a personal GenAI learning project focused on enterprise-grade Java backend development and AI-powered knowledge management systems.
