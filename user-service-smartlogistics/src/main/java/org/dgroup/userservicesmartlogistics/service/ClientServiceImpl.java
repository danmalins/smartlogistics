package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.exception.ClientNotFoundException;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.repository.ClientProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientProfileRepository clientProfileRepository;

    @Override
    public ClientProfile getClient(String email, Authentication authentication) {
        if (!isManager(authentication) && !isAdmin(authentication))
            throw new CustomAccessDeniedException("Only manager or admin can get driver profile");
        return clientProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
    }

    @Override
    public List<ClientProfile> getAllClients(Authentication authentication) {
        if (!isManager(authentication) && !isAdmin(authentication))
            throw new CustomAccessDeniedException("Only manager or admin can get client profiles");
        return clientProfileRepository.findAll();
    }

    @Override
    public ClientProfile getClientProfile(Authentication authentication) {
        String email = authentication.getName();

        return clientProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new ClientNotFoundException("Client with email: '" + email + "' not found"));
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }

    private boolean isManager(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_MANAGER"));
    }
}
