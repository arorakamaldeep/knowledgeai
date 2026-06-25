package com.knowledgeai.document.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.knowledgeai.document.dto.DocumentResponse;
import com.knowledgeai.document.entity.Document;
import com.knowledgeai.document.repository.DocumentRepository;
import com.knowledgeai.document.util.PdfUtil;

import io.jsonwebtoken.io.IOException;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	private static final String UPLOAD_DIR = "uploads/";

	@Override
	public DocumentResponse uploadDocument(MultipartFile file) {

		try {

			Files.createDirectories(Paths.get(UPLOAD_DIR));
			String filePath = UPLOAD_DIR + file.getOriginalFilename();

			Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
			
			String extractedText = "";

			if (file.getOriginalFilename()
			        .toLowerCase()
			        .endsWith(".pdf")) {

			    extractedText =
			            PdfUtil.extractText(file.getInputStream());
			}
			
			Document document = Document.builder().fileName(file.getOriginalFilename())
					.contentType(file.getContentType()).fileSize(file.getSize()).filePath(filePath)
					.uploadedAt(LocalDateTime.now()).extractedText(extractedText).build();

			Document saved = documentRepository.save(document);

			return DocumentResponse.builder().id(saved.getId()).fileName(saved.getFileName())
					.message("Document uploaded successfully").build();

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Document findDocumentById(Long id) {
		return documentRepository.findById(id)
	            .orElseThrow(() ->
                new RuntimeException("Document not found with id: " + id));
	}

}
