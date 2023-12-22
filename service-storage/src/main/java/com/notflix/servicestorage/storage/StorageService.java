package com.notflix.servicestorage.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(MultipartFile file, String user, String filename);

	Stream<Path> loadAll();

  Stream<Path> loadAll(String user);

	Path load(String filename, String user);

	Resource loadAsResource(String filename, String user);

	void deleteAll();

  void deleteAll(String user);
}