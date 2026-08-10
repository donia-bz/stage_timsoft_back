// Migration MongoDB pour aligner les statuts sur le modèle First Delivery/Navex
// Exécution : mongosh bfexpress db/migration/V1__migrate_statuts.js

// Mapping des anciens statuts vers les nouveaux
const MAPPING_COMMANDES = {
    'EN_ATTENTE': 'EN_ATTENTE',        // Inchangé
    'CONFIRMEE': 'MANIFESTE',          // Confirmed → Manifeste (inclus dans manifeste)
    'EN_LIVRAISON': 'EN_LIVRAISON',    // Inchangé
    'LIVREE': 'LIVRE',                 // LIVREE → LIVRE (nom exact)
    'ANNULEE': 'ANNULEE'               // Inchangé
};

const MAPPING_COLIS = {
    'EN_ATTENTE': 'EN_ATTENTE',        // Inchangé
    'NON_SERIEUX': 'ANNULEE',          // Non sérieux → Annulé
    'A_VERIFIER': 'EN_ATTENTE',        // À vérifier → En attente
    'A_ENLEVER': 'A_ENLEVER',         // Inchangé
    'ENLEVE': 'ENLEVE',               // Inchangé
    'AU_DEPOT': 'AU_DEPOT',           // Inchangé
    'RETOUR_DEPOT': 'RETOUR_DEPOT',   // Inchangé
    'EN_TRANSIT': 'EN_LIVRAISON',     // En transit → En livraison
    'LIVRE': 'LIVRE',                 // Inchangé
    'LIVRE_PAYE': 'LIVRE_PAYE',       // Inchangé
    'ECHANGE': 'RETOUR_DEPOT',        // Échange → Retour dépôt
    'REMBOURSE': 'RETOUR_EXPEDITEUR' // Remboursé → Retour expéditeur
};

// Migration des commandes
print("=== Migration des statuts de commandes ===");
const commandeCount = db.commandes.countDocuments({});
print(`Nombre total de commandes : ${commandeCount}`);

let commandeUpdated = 0;
db.commandes.find({}).forEach(function(doc) {
    const oldStatut = doc.statut;
    const newStatut = MAPPING_COMMANDES[oldStatut];

    if (newStatut && newStatut !== oldStatut) {
        db.commandes.updateOne(
            { _id: doc._id },
            { $set: { statut: newStatut } }
        );
        print(`Commande ${doc._id}: ${oldStatut} → ${newStatut}`);
        commandeUpdated++;
    }
});
print(`Commandes mises à jour : ${commandeUpdated}`);

// Migration des colis
print("\n=== Migration des statuts de colis ===");
const colisCount = db.colis.countDocuments({});
print(`Nombre total de colis : ${colisCount}`);

let colisUpdated = 0;
db.colis.find({}).forEach(function(doc) {
    const oldStatut = doc.statut;
    const newStatut = MAPPING_COLIS[oldStatut];

    if (newStatut && newStatut !== oldStatut) {
        db.colis.updateOne(
            { _id: doc._id },
            { $set: { statut: newStatut } }
        );
        print(`Colis ${doc._id}: ${oldStatut} → ${newStatut}`);
        colisUpdated++;
    }
});
print(`Colis mis à jour : ${colisUpdated}`);

// Vérification
print("\n=== Vérification après migration ===");
const commandeStats = db.commandes.aggregate([
    { $group: { _id: "$statut", count: { $sum: 1 } } },
    { $sort: { _id: 1 } }
]).toArray();
print("Distribution des statuts de commandes :");
commandeStats.forEach(stat => print(`  ${stat._id}: ${stat.count}`));

const colisStats = db.colis.aggregate([
    { $group: { _id: "$statut", count: { $sum: 1 } } },
    { $sort: { _id: 1 } }
]).toArray();
print("Distribution des statuts de colis :");
colisStats.forEach(stat => print(`  ${stat._id}: ${stat.count}`));

print("\n=== Migration terminée avec succès ===");
