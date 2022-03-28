package com.dokobit.archive.controller;

import com.dokobit.archive.service.StatisticsLogService;
import com.dokobit.archive.service.ZipService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CompressorController {

    private final ZipService zipService;
    private final StatisticsLogService statisticsLogService;
    private final HttpServletRequest httpServletRequest;

    @RequestMapping(method = RequestMethod.POST, value="/zip", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> archiveFiles(@RequestPart("files") List<MultipartFile> files) {
        statisticsLogService.logRequest(httpServletRequest.getRequestURI(), httpServletRequest.getRemoteAddr());
        return ResponseEntity.status(200)
                .contentType(MediaType.parseMediaType("application/zip"))
                .header("Content-Disposition", "attachment; filename=test.zip")
                .body(out -> zipService.zipFiles(out, files));
    }
}
