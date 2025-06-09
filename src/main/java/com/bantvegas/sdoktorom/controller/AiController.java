package com.bantvegas.sdoktorom.controller;

import com.bantvegas.sdoktorom.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private AiService aiService;

    @PostMapping(value = "/recommend", consumes = {"multipart/form-data"})
    public Map<String, String> recommend(
            @RequestPart(value = "symptoms", required = false) String symptoms,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {
        String result = aiService.getRecommendation(symptoms, image);
        return Map.of("result", result);
    }
}

