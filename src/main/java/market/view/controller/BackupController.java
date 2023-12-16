package market.view.controller;

import lombok.RequiredArgsConstructor;
import market.service.DatabaseBackupService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/admin/backup")
@RequiredArgsConstructor
public class BackupController {
    private final DatabaseBackupService databaseBackupService;

    private static final String BACKUP_FOLDER = "D:\\backup";

    @GetMapping
    public String getBackup() {
        return "admin/backup";
    }

    @GetMapping("/createBackup")
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {
        databaseBackupService.createBackup();
        // Замените путь к файлу на реальный путь на вашем сервере
        String filePath = "data.json";
        File file = new File(filePath);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.json");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
