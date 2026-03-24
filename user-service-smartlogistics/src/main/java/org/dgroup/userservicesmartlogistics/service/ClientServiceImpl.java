package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.exception.ClientNotFoundException;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.repository.ClientProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientProfileRepository clientProfileRepository;

    @Override
    public ClientProfile getClientProfile(Authentication authentication) {
        String email = authentication.getName();

        return clientProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new ClientNotFoundException("Client profile not found"));
    }
}
