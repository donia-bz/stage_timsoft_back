package tn.esprit.commandes.service;

import tn.esprit.commandes.entity.Destinataire;

import java.util.List;

public interface DestinataireService {

    Destinataire createDestinataire(Destinataire destinataire);

    Destinataire getDestinataireById(String id);

    List<Destinataire> getAllDestinataires();

    Destinataire updateDestinataire(String id, Destinataire destinataire);

    void deleteDestinataire(String id);
}
