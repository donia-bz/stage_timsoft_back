package tn.esprit.commandes.service;

import tn.esprit.commandes.entity.enums.StatutCommande;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Service de validation des transitions de statut selon les règles métier First Delivery/Navex
 * Définit les transitions autorisées et rejette toute transition non conforme.
 */
public class StatutTransitionService {

    private static final EnumMap<StatutCommande, Set<StatutCommande>> TRANSITIONS_AUTORISEES;

    static {
        TRANSITIONS_AUTORISEES = new EnumMap<>(StatutCommande.class);

        // EN_ATTENTE → MANIFESTE | A_ENLEVER | ANNULEE
        Set<StatutCommande> depuisEnAttente = new HashSet<>();
        depuisEnAttente.add(StatutCommande.MANIFESTE);
        depuisEnAttente.add(StatutCommande.A_ENLEVER);
        depuisEnAttente.add(StatutCommande.ANNULEE);
        TRANSITIONS_AUTORISEES.put(StatutCommande.EN_ATTENTE, depuisEnAttente);

        // MANIFESTE → A_ENLEVER | ANNULEE
        Set<StatutCommande> depuisManifeste = new HashSet<>();
        depuisManifeste.add(StatutCommande.A_ENLEVER);
        depuisManifeste.add(StatutCommande.ANNULEE);
        TRANSITIONS_AUTORISEES.put(StatutCommande.MANIFESTE, depuisManifeste);

        // A_ENLEVER → ENLEVE | ANNULEE
        Set<StatutCommande> depuisAEnlever = new HashSet<>();
        depuisAEnlever.add(StatutCommande.ENLEVE);
        depuisAEnlever.add(StatutCommande.ANNULEE);
        TRANSITIONS_AUTORISEES.put(StatutCommande.A_ENLEVER, depuisAEnlever);

        // ENLEVE → AU_DEPOT | EN_LIVRAISON
        Set<StatutCommande> depuisEnleve = new HashSet<>();
        depuisEnleve.add(StatutCommande.AU_DEPOT);
        depuisEnleve.add(StatutCommande.EN_LIVRAISON);
        TRANSITIONS_AUTORISEES.put(StatutCommande.ENLEVE, depuisEnleve);

        // AU_DEPOT → EN_LIVRAISON | RETOUR_EXPEDITEUR
        Set<StatutCommande> depuisAuDepot = new HashSet<>();
        depuisAuDepot.add(StatutCommande.EN_LIVRAISON);
        depuisAuDepot.add(StatutCommande.RETOUR_EXPEDITEUR);
        TRANSITIONS_AUTORISEES.put(StatutCommande.AU_DEPOT, depuisAuDepot);

        // EN_LIVRAISON → LIVRE | LIVRE_PAYE | ECHEC_LIVRAISON | RETOUR_DEPOT
        Set<StatutCommande> depuisEnLivraison = new HashSet<>();
        depuisEnLivraison.add(StatutCommande.LIVRE);
        depuisEnLivraison.add(StatutCommande.LIVRE_PAYE);
        depuisEnLivraison.add(StatutCommande.ECHEC_LIVRAISON);
        depuisEnLivraison.add(StatutCommande.RETOUR_DEPOT);
        TRANSITIONS_AUTORISEES.put(StatutCommande.EN_LIVRAISON, depuisEnLivraison);

        // ECHEC_LIVRAISON → EN_LIVRAISON | RETOUR_DEPOT | RETOUR_EXPEDITEUR
        Set<StatutCommande> depuisEchec = new HashSet<>();
        depuisEchec.add(StatutCommande.EN_LIVRAISON);
        depuisEchec.add(StatutCommande.RETOUR_DEPOT);
        depuisEchec.add(StatutCommande.RETOUR_EXPEDITEUR);
        TRANSITIONS_AUTORISEES.put(StatutCommande.ECHEC_LIVRAISON, depuisEchec);

        // RETOUR_DEPOT → RETOUR_EXPEDITEUR | EN_LIVRAISON (re-tentative)
        Set<StatutCommande> depuisRetourDepot = new HashSet<>();
        depuisRetourDepot.add(StatutCommande.RETOUR_EXPEDITEUR);
        depuisRetourDepot.add(StatutCommande.EN_LIVRAISON);
        TRANSITIONS_AUTORISEES.put(StatutCommande.RETOUR_DEPOT, depuisRetourDepot);

        // LIVRE → LIVRE_PAYE (confirmation COD après coup)
        Set<StatutCommande> depuisLivree = new HashSet<>();
        depuisLivree.add(StatutCommande.LIVRE_PAYE);
        TRANSITIONS_AUTORISEES.put(StatutCommande.LIVRE, depuisLivree);

        // Statuts terminaux (AUCUNE transition autorisée)
        TRANSITIONS_AUTORISEES.put(StatutCommande.LIVRE_PAYE, new HashSet<>());
        TRANSITIONS_AUTORISEES.put(StatutCommande.RETOUR_EXPEDITEUR, new HashSet<>());
        TRANSITIONS_AUTORISEES.put(StatutCommande.ANNULEE, new HashSet<>());
    }

    /**
     * Vérifie si une transition de statut est autorisée
     *
     * @param actuel     Statut actuel
     * @param nouveau    Nouveau statut demandé
     * @return true si la transition est autorisée
     */
    public static boolean estTransitionAutorisee(StatutCommande actuel, StatutCommande nouveau) {
        if (actuel == null || nouveau == null) {
            return false;
        }

        // Même statut = pas de transition, autorisé (idempotent)
        if (actuel == nouveau) {
            return true;
        }

        Set<StatutCommande> transitionsPossibles = TRANSITIONS_AUTORISEES.get(actuel);
        return transitionsPossibles != null && transitionsPossibles.contains(nouveau);
    }

    /**
     * Retourne un message d'erreur explicite pour une transition non autorisée
     *
     * @param actuel     Statut actuel
     * @param nouveau    Nouveau statut demandé
     * @return Message d'erreur
     */
    public static String getMessageErreur(StatutCommande actuel, StatutCommande nouveau) {
        if (actuel == null) {
            return "Statut actuel non défini";
        }
        if (nouveau == null) {
            return "Nouveau statut non défini";
        }
        if (actuel == nouveau) {
            return "Aucune transition nécessaire (statut identique)";
        }

        Set<StatutCommande> transitionsPossibles = TRANSITIONS_AUTORISEES.get(actuel);
        if (transitionsPossibles == null || transitionsPossibles.isEmpty()) {
            return String.format("Le statut %s est terminal, aucune transition n'est autorisée", actuel);
        }

        return String.format(
                "Transition de %s vers %s non autorisée. Transitions possibles depuis %s : %s",
                actuel, nouveau, actuel, String.join(", ", transitionsPossibles.stream().map(Enum::name).toList())
        );
    }

    /**
     * Valide une transition et lance une exception si non autorisée
     *
     * @param actuel     Statut actuel
     * @param nouveau    Nouveau statut demandé
     * @throws IllegalStateException si la transition n'est pas autorisée
     */
    public static void validerTransition(StatutCommande actuel, StatutCommande nouveau) {
        if (!estTransitionAutorisee(actuel, nouveau)) {
            throw new IllegalStateException(getMessageErreur(actuel, nouveau));
        }
    }
}
