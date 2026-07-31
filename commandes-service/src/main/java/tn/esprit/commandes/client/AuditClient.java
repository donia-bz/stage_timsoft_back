package tn.esprit.commandes.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuditClient {

    private final RestTemplate restTemplate;

    @Value("${audit.service.url:http://localhost:8087}")
    private String auditServiceUrl;

    public void enregistrerChangementStatut(String entiteType, String entiteId,
                                            String ancienStatut, String nouveauStatut) {
        try {
            Map<String, String> request = new HashMap<>();
            request.put("entiteType", entiteType);
            request.put("entiteId", entiteId);
            request.put("ancienStatut", ancienStatut);
            request.put("nouveauStatut", nouveauStatut);
            restTemplate.postForEntity(auditServiceUrl + "/api/historique-statut", request, Void.class);
        } catch (Exception e) {
            System.err.println("Audit non enregistré pour " + entiteType + "/" + entiteId + " : " + e.getMessage());
        }
    }
}
