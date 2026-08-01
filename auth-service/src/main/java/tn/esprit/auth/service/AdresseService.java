package tn.esprit.auth.service;

import tn.esprit.auth.entity.Adresse;

import java.util.List;

public interface AdresseService {

    Adresse createAdresse(Adresse adresse);

    Adresse getAdresseById(String id);

    List<Adresse> getAllAdresses();

    Adresse updateAdresse(String id, Adresse adresse);

    void deleteAdresse(String id);
}
