package com.example.georgestore.application.service;

import com.example.georgestore.domain.repository.ClientRepository;
import com.example.georgestore.dto.ClientRequest;
import com.example.georgestore.dto.ClientResponse;
import com.example.georgestore.infrastructure.persistence.entity.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientResponse create(ClientRequest req) {

        if (clientRepository.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already registered");

        ClientEntity client = ClientEntity.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .build();

        clientRepository.save(client);

        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone()
        );
    }

    public List<ClientResponse> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(c -> new ClientResponse(
                        c.getId(),
                        c.getName(),
                        c.getEmail(),
                        c.getPhone()
                )).toList();
    }

    public ClientResponse findById(Long id) {
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone()
        );
    }

    public ClientResponse update(Long id, ClientRequest req) {

        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        if (!client.getEmail().equals(req.getEmail()) &&
                clientRepository.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already exists");

        client.setName(req.getName());
        client.setEmail(req.getEmail());
        client.setPhone(req.getPhone());

        clientRepository.save(client);

        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone()
        );
    }

    public void delete(Long id) {
        if (!clientRepository.existsById(id))
            throw new IllegalArgumentException("Client not found");

        clientRepository.deleteById(id);
    }
}
