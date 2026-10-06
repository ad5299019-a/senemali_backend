package com.senemali.api;

import com.senemali.api.serviceImpl.FileStorageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileStorageServiceImplTests {

    @TempDir
    Path temporaryDirectory;

    @Test
    void savesVideoInTutorialFolderAndReturnsItsPublicUrl() {
        FileStorageServiceImpl storage = createStorage();
        MockMultipartFile video = new MockMultipartFile(
                "file", "lesson.mp4", "video/mp4", new byte[]{1, 2, 3}
        );

        String fileName = storage.saveFile(video, "tutoriels");

        assertTrue(Files.exists(temporaryDirectory.resolve("tutoriels").resolve(fileName)));
        assertEquals(
                "http://localhost:8080/uploads/tutoriels/" + fileName,
                storage.getFileUrl(fileName, "tutoriels")
        );
    }

    @Test
    void rejectsVideoOutsideTutorialFolder() {
        FileStorageServiceImpl storage = createStorage();
        MockMultipartFile video = new MockMultipartFile(
                "file", "lesson.mp4", "video/mp4", new byte[]{1, 2, 3}
        );

        assertThrows(RuntimeException.class, () -> storage.saveFile(video, "produits"));
    }

    private FileStorageServiceImpl createStorage() {
        FileStorageServiceImpl storage = new FileStorageServiceImpl();
        ReflectionTestUtils.setField(storage, "uploadDir", temporaryDirectory.toString());
        ReflectionTestUtils.setField(storage, "baseUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(storage, "maxImageSize", 10L * 1024 * 1024);
        ReflectionTestUtils.setField(storage, "maxVideoSize", 100L * 1024 * 1024);
        return storage;
    }
}
