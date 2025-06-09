package com.bantvegas.sdoktorom.service;

import com.bantvegas.sdoktorom.dto.MessageDto;
import com.bantvegas.sdoktorom.model.Message;
import com.bantvegas.sdoktorom.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // --- Pre lekára ---
    public List<MessageDto> getMessagesForDoctor(Long doctorId) {
        List<Message> messages = messageRepository.findByDoctorId(doctorId);
        return messages.stream().map(this::toDto).collect(Collectors.toList());
    }

    // --- Pre pacienta ---
    public List<MessageDto> getMessagesForPatient(Long patientId) {
        List<Message> messages = messageRepository.findByPatientId(patientId);
        return messages.stream().map(this::toDto).collect(Collectors.toList());
    }

    // --- Uloženie analýzy pacienta ---
    public MessageDto saveAnalysis(Long patientId, String symptoms, String recommendation) {
        Message message = new Message();
        message.setPatientId(patientId);
        message.setSymptoms(symptoms);
        message.setRecommendation(recommendation);
        message.setCreatedAt(java.time.LocalDateTime.now());
        message.setSentToDoctor(false);
        message.setViewedByDoctor(false);

        Message saved = messageRepository.save(message);
        return toDto(saved);
    }

    // --- Odpoveď doktora na správu ---
    public MessageDto replyToMessage(Long messageId, String recommendation) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setRecommendation(recommendation);
        message.setViewedByDoctor(true);

        Message saved = messageRepository.save(message);
        return toDto(saved);
    }

    // --- Odoslanie správy doktorovi ---
    public MessageDto sendMessageToDoctor(Long patientId, Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!message.getPatientId().equals(patientId)) {
            throw new RuntimeException("Message does not belong to the patient");
        }

        message.setSentToDoctor(true);
        Message saved = messageRepository.save(message);
        return toDto(saved);
    }

    // --- Premena entity na DTO ---
    private MessageDto toDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setDoctorId(message.getDoctorId());
        dto.setPatientId(message.getPatientId());
        dto.setSymptoms(message.getSymptoms());
        dto.setCreatedAt(message.getCreatedAt());
        dto.setRecommendation(message.getRecommendation());
        dto.setViewedByDoctor(message.getViewedByDoctor());
        dto.setSentToDoctor(message.getSentToDoctor());
        return dto;
    }
}
