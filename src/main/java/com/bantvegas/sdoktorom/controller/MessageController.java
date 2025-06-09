package com.bantvegas.sdoktorom.controller;

import com.bantvegas.sdoktorom.dto.MessageDto;
import com.bantvegas.sdoktorom.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // --- Získanie správ pre doktora ---
    @GetMapping("/for-doctor/{doctorId}")
    public ResponseEntity<List<MessageDto>> getMessagesForDoctor(@PathVariable Long doctorId) {
        List<MessageDto> messages = messageService.getMessagesForDoctor(doctorId);
        return ResponseEntity.ok(messages);
    }

    // --- Získanie správ pre pacienta ---
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MessageDto>> getMessagesForPatient(@PathVariable Long patientId) {
        List<MessageDto> messages = messageService.getMessagesForPatient(patientId);
        return ResponseEntity.ok(messages);
    }

    // --- Odpoveď doktora na správu ---
    @PostMapping("/{messageId}/reply")
    public ResponseEntity<MessageDto> replyToMessage(@PathVariable Long messageId, @RequestBody RecommendationRequest request) {
        MessageDto updatedMessage = messageService.replyToMessage(messageId, request.getRecommendation());
        return ResponseEntity.ok(updatedMessage);
    }

    // --- Uloženie analýzy pacienta ---
    @PostMapping("/patient/{patientId}/save-analysis")
    public ResponseEntity<MessageDto> saveAnalysis(
            @PathVariable Long patientId,
            @RequestBody AnalysisRequest request) {

        MessageDto saved = messageService.saveAnalysis(patientId, request.getSymptoms(), request.getRecommendation());
        return ResponseEntity.ok(saved);
    }

    // --- Odoslanie správy doktorovi ---
    @PostMapping("/patient/{patientId}/send-to-doctor/{messageId}")
    public ResponseEntity<MessageDto> sendMessageToDoctor(
            @PathVariable Long patientId,
            @PathVariable Long messageId) {

        MessageDto sent = messageService.sendMessageToDoctor(patientId, messageId);
        return ResponseEntity.ok(sent);
    }

    // DTO triedy na prijímanie JSON requestov
    public static class RecommendationRequest {
        private String recommendation;

        public String getRecommendation() { return recommendation; }
        public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    }

    public static class AnalysisRequest {
        private String symptoms;
        private String recommendation;

        public String getSymptoms() { return symptoms; }
        public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

        public String getRecommendation() { return recommendation; }
        public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    }
}
