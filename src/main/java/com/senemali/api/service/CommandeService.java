package com.senemali.api.service;

import com.senemali.api.dto.request.CommandeRequest;
import com.senemali.api.dto.response.CommandeResponse;
import com.senemali.api.enums.StatutCommandeEnum;
import java.util.List;

public interface CommandeService {
    CommandeResponse create(CommandeRequest request);
    List<CommandeResponse> findAll();
    CommandeResponse findById(Long id);
    List<CommandeResponse> findMine();
    long countMySales();
    List<CommandeResponse> findByUtilisateur(Long idUtilisateur);
    CommandeResponse confirmer(Long id);
    CommandeResponse updateStatut(Long id, StatutCommandeEnum statut);
}
