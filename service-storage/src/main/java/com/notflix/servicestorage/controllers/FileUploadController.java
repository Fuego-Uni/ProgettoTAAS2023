package com.notflix.servicestorage.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.notflix.servicestorage.storage.StorageFileNotFoundException;
import com.notflix.servicestorage.storage.StorageService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/storage")
public class FileUploadController {
	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

  @GetMapping("/")
	public String listUploadedFiles(HttpServletRequest request) throws IOException {
    String user = request.getHeader("Authorization");

    storageService.loadAll(user).forEach(System.out::println);

    return "done";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(HttpServletRequest request, @PathVariable String filename) {
    String user = request.getHeader("Authorization");

		Resource file = storageService.loadAsResource(filename, user);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/upload")
	public String handleFileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
    String user = request.getHeader("Authorization");

		storageService.store(file, user, file.getOriginalFilename());

    System.out.println("File uploaded: " + file.getOriginalFilename());

    return "file uploaded";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}