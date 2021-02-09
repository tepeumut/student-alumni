package com.umuttepe.studentalumni.storage;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    public void init();

    public void save(MultipartFile file, String fileName);

    public Resource load(String filename) throws Exception;

    public void deleteAll();

    public Stream<Path> loadAll();
}
