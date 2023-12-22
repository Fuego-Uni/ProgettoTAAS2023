package com.notflix.servicestorage.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

  private final Path rootLocation;

  @Autowired
  public FileSystemStorageService(StorageProperties properties) {

    if (properties.getLocation().trim().length() == 0) {
      throw new StorageException("File upload location can not be Empty.");
    }

    this.rootLocation = Paths.get(properties.getLocation());
  }

  @Override
  public void store(MultipartFile file, String user, String filename) {
    try {
      if (file.isEmpty()) { throw new StorageException("Failed to store empty file."); }

      if (user == null || user.trim().length() == 0) { throw new StorageException("User can not be empty."); }

      // check if the user directory exists
      if (!Files.exists(this.rootLocation.resolve(user))) {
        System.out.println("creating user directory");
        Files.createDirectories(this.rootLocation.resolve(user));
      }

      // file location must be: uploads/user/filename
      Path destinationFile = this.rootLocation.resolve(user).resolve(filename).normalize().toAbsolutePath();

      System.out.println("destinationFile: " + destinationFile);

      // check if the upload directory is a subdirectory of the root location
      if (!destinationFile.startsWith(this.rootLocation.toAbsolutePath())) {
        // This is a security check
        throw new StorageException("Cannot store file outside current directory." + destinationFile);
      }

      // check if the folder exists
      if (!Files.exists(this.rootLocation)) {
        System.out.println("creating directory");
        Files.createDirectories(this.rootLocation);
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new StorageException("Failed to store file.", e);
    }
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.rootLocation, 2)
          .filter(path -> !path.equals(this.rootLocation))
          .map(this.rootLocation::relativize);
    } catch (IOException e) {
      throw new StorageException("Failed to read stored files", e);
    }
  }

  @Override
  public Stream<Path> loadAll(String user) {
    try {
      // check if the user directory exists
      if (!Files.exists(this.rootLocation.resolve(user))) {
        System.out.println("creating user directory");
        Files.createDirectories(this.rootLocation.resolve(user));
      }
      
      return Files.walk(this.rootLocation.resolve(user), 1)
          .filter(path -> !path.equals(this.rootLocation.resolve(user)))
          .map(this.rootLocation.resolve(user)::relativize);
      
    } catch (IOException e) {
      throw new StorageException("Failed to read stored files", e);
    }
  }

  @Override
  public Path load(String filename, String user) {
    try {
      if (!Files.exists(this.rootLocation.resolve(user))) {
        System.out.println("creating user directory");
        Files.createDirectories(this.rootLocation.resolve(user));
      }
    } catch (IOException e) {
      throw new StorageException("Failed to read stored files", e);
    }
    
    return rootLocation.resolve(user).resolve(filename);
  }

  @Override
  public Resource loadAsResource(String filename, String user) {
    try {
      Path file = load(filename, user);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException("Could not read file: " + filename);
      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filename, e);
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }

  @Override
  public void deleteAll(String user) {
    if (!Files.exists(this.rootLocation.resolve(user))) { return; }

    FileSystemUtils.deleteRecursively(rootLocation.resolve(user).toFile());
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }
}