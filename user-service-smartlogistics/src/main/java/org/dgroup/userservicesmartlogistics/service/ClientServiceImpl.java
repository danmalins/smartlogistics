package org.dgroup.userservicesmartlogistics.service;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateClientProfileRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.ClientNotFoundException;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.UserNotFoundException;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.repository.ClientProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientProfileRepository clientProfileRepository;

    @Override
    public ClientProfile getClientProfile(Authentication authentication) {
        String email = authentication.getName();

        return clientProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new ClientNotFoundException("Client profile not found"));
    }

    @Override
    public ClientProfile updateClientProfile(UpdateClientProfileRequestDTO requestDTO, Authentication authentication) {
        String email = authentication.getName();
        ClientProfile clientProfile = clientProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new ClientNotFoundException("Client profile not found"));

        if (!isAdmin(authentication) && email.equals(authentication.getName()))
            throw new CustomAccessDeniedException("You can only update your own profile.");



        return null;
    }

    @Override
    public void deleteClientProfile(Authentication authentication) {

    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
