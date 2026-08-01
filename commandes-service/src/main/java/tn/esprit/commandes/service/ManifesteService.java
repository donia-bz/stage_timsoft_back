package tn.esprit.commandes.service;

import tn.esprit.commandes.entity.Manifeste;

import java.util.List;

public interface ManifesteService {

    Manifeste createManifeste(Manifeste manifeste);

    Manifeste getManifesteById(String id);

    List<Manifeste> getManifestesByClient(String clientId);

    List<Manifeste> getAllManifestes();

    Manifeste updateManifeste(String id, Manifeste manifeste);

    void deleteManifeste(String id);
}
