package tn.esprit.commandes.entity.enums;

/**
 * Statuts alignés sur le modèle First Delivery / Navex (transporteur B2B Tunisie)
 * Cycle de vie complet : création → manifeste → enlèvement → dépôt → livraison → retours
 */
public enum StatutCommande {
    // Création
    EN_ATTENTE,          // créée par l'expéditeur, pas encore sur manifeste validé

    // Manifeste
    MANIFESTE,           // incluse dans un manifeste (brouillon validé ou manifeste créé)

    // Enlèvement
    A_ENLEVER,          // planifiée pour enlèvement chez l'expéditeur
    ENLEVE,              // enlevée chez l'expéditeur

    // Dépôt / Hub
    AU_DEPOT,            // arrivée au hub / dépôt

    // Livraison
    EN_LIVRAISON,        // sortie en tournée livreur
    LIVRE,               // livrée avec succès (cash éventuellement à confirmer)
    LIVRE_PAYE,          // livrée + COD encaissé

    // Échecs / Retours
    ECHEC_LIVRAISON,     // destinataire absent / refus / report
    RETOUR_DEPOT,        // retourné au dépôt
    RETOUR_EXPEDITEUR,   // rendu à l'expéditeur

    // Terminal
    ANNULEE              // annulée (uniquement vraie annulation, PAS les retours)
}
