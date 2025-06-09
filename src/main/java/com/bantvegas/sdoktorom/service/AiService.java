package com.bantvegas.sdoktorom.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class AiService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${openai.api.url}")
    private String openaiApiUrl;

    public String getRecommendation(String symptoms, MultipartFile image) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        // PROMPT: Dôraz na odstavce (prázdne riadky medzi bodmi), odbornosť a prehľadnosť
        String systemPrompt = """
            Si AI zdravotný asistent, ktorý analyzuje symptómy alebo fotografie zdravotných problémov.
            Tvoja odpoveď MUSÍ byť v slovenčine a formátovaná do odsekov (medzi každým bodom prázdny riadok).
            Odpovedaj odborne, jasne, upokojujúco, bez diagnóz.
            Vždy rozdeľ odpoveď aspoň na:
            1. Úvodný opis (čo je na fotke / čo človek píše).
            2. Možné príčiny alebo ďalší postup (čo odporúčaš sledovať alebo spraviť).
            3. Varovanie alebo odporúčanie, kedy ísť k lekárovi.
            Neodporúčaj žiadne konkrétne lieky a nevyslovuj diagnózu.
            """;

        List<Map<String, Object>> messages = new ArrayList<>();
        messages.add(Map.of(
                "role", "system",
                "content", systemPrompt
        ));

        // Užívateľský vstup (text/obrázok)
        if (image != null && !image.isEmpty()) {
            // Base64 pre OpenAI API
            byte[] imgBytes = image.getBytes();
            String imgBase64 = Base64.getEncoder().encodeToString(imgBytes);

            List<Object> content = new ArrayList<>();
            if (symptoms != null && !symptoms.isBlank()) {
                content.add(Map.of("type", "text", "text", symptoms));
            }
            content.add(Map.of(
                    "type", "image_url",
                    "image_url", Map.of("url", "data:image/jpeg;base64," + imgBase64)
            ));

            messages.add(Map.of(
                    "role", "user",
                    "content", content
            ));
        } else {
            // Len text
            messages.add(Map.of(
                    "role", "user",
                    "content", (symptoms == null || symptoms.isBlank()) ?
                            "Žiadne príznaky ani obrázok neboli zadané." : symptoms
            ));
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o");
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 900);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(openaiApiUrl, request, Map.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            var choices = (List<Map<String, Object>>) response.getBody().get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                if (message != null && message.get("content") != null) {
                    // Urob ešte rozumné trimovanie a odstránenie nadbytočných úvodzoviek alebo markdownu
                    String raw = message.get("content").toString().trim();
                    // Odstráň markdown numbering a extra znaky ak treba
                    raw = raw.replaceAll("^[\\s`'\"]+|[\\s`'\"]+$", ""); // začiatok/koniec úvodzoviek, apostrofov, backtickov
                    return raw;
                }
            }
        }
        throw new RuntimeException("Neočakávaná odpoveď z OpenAI API: " + response);
    }
}

