package market.view.rest;


import lombok.RequiredArgsConstructor;
import market.service.impl.ExceptionServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v3/errors")
@RequiredArgsConstructor
public class ExceptionRestController {
    private final ExceptionServiceImpl exceptionService;
    @GetMapping(value = "/picture")
    public ResponseEntity<?> findAvatar() {
        return exceptionService.findPicture()
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }
}
