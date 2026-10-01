-- Donnees de demonstration Senemali API (MySQL)
-- A executer une seule fois sur une base vide apres le demarrage de l'application,
-- qui cree/met a jour le schema Hibernate.
-- Avec InheritanceType.JOINED, les tables specialisees utilisent leurs colonnes
-- de jointure comme cle primaire : id_administrateur, id_acheteur,
-- id_agriculteur et id_fournisseur. Elles referencent utilisateurs.id.
--
-- Comptes de demonstration : le mot de passe en clair est SenemaliTest2026!
-- Le hash BCrypt ci-dessous correspond a ce mot de passe pour les 12 comptes.
-- Ne pas reutiliser ces identifiants/mots de passe en production.
-- Les identifiants sont fixes : ne pas executer ce script sur une base contenant
-- deja des donnees de production ou les memes identifiants.

USE senemali;
START TRANSACTION;

-- 1. Utilisateurs racine : admins, acheteurs, agriculteurs et fournisseurs
INSERT INTO utilisateurs
    (id, nom, prenom, email, telephone, localisation, mot_de_passe,
     photo_profil, role, statut, date_creation)
VALUES
    (1001, 'Aminata', 'Traore', 'admin1@senemali.ml', '+22370001001', 'Bamako', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'ADMIN', 1, '2026-01-05 09:00:00'),
    (1002, 'Moussa', 'Coulibaly', 'admin2@senemali.ml', '+22370001002', 'Sikasso', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'ADMIN', 1, '2026-01-06 09:00:00'),
    (1003, 'Fatoumata', 'Keita', 'admin3@senemali.ml', '+22370001003', 'Segou', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'ADMIN', 1, '2026-01-07 09:00:00'),
    (2001, 'Boubacar', 'Diallo', 'acheteur1@senemali.ml', '+22370002001', 'Bamako', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'ACHETEUR', 1, '2026-01-08 09:00:00'),
    (2002, 'Awa', 'Sangare', 'acheteur2@senemali.ml', '+22370002002', 'Kayes', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'ACHETEUR', 1, '2026-01-09 09:00:00'),
    (2003, 'Oumar', 'Diarra', 'acheteur3@senemali.ml', '+22370002003', 'Mopti', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'ACHETEUR', 1, '2026-01-10 09:00:00'),
    (3001, 'Mariam', 'Touré', 'agriculteur1@senemali.ml', '+22370003001', 'Sikasso', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'AGRICULTEUR', 1, '2026-01-11 09:00:00'),
    (3002, 'Ibrahim', 'Konate', 'agriculteur2@senemali.ml', '+22370003002', 'Segou', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'AGRICULTEUR', 1, '2026-01-12 09:00:00'),
    (3003, 'Fanta', 'Cisse', 'agriculteur3@senemali.ml', '+22370003003', 'Koutiala', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'AGRICULTEUR', 1, '2026-01-13 09:00:00'),
    (4001, 'Sekou', 'Maiga', 'fournisseur1@senemali.ml', '+22370004001', 'Bamako', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'FOURNISSEUR', 1, '2026-01-14 09:00:00'),
    (4002, 'Hawa', 'Berthe', 'fournisseur2@senemali.ml', '+22370004002', 'Sikasso', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'FOURNISSEUR', 1, '2026-01-15 09:00:00'),
    (4003, 'Modibo', 'Sissoko', 'fournisseur3@senemali.ml', '+22370004003', 'Kayes', '$2a$12$.//P5agyGrONjOYPid/lbeTCh9D5U9i2pVCjG0NEAuG/wavHH2NxO', NULL, 'FOURNISSEUR', 1, '2026-01-16 09:00:00');

-- 2. Categories, puis tables specialisees des utilisateurs (heritage JOINED)
INSERT INTO `Categorie_Cultures` (id_categorie, nom_categorie, description)
VALUES
    (1, 'Cereales', 'Cultures cerealieres'),
    (2, 'Legumineuses', 'Cultures de legumes secs'),
    (3, 'Cultures maraicheres', 'Legumes et cultures de jardin');

INSERT INTO administrateurs (id_administrateur) VALUES (1001), (1002), (1003);
INSERT INTO acheteurs (id_acheteur) VALUES (2001), (2002), (2003);
INSERT INTO agriculteurs (id_agriculteur, descripion)
VALUES (3001, 'Production de mais et mil'), (3002, 'Production de riz'), (3003, 'Maraichage');
INSERT INTO fournisseurs
    (id_fournisseur, description, nom_entrepise, zone_de_vente, statut_fournisseur, id_administrateur_validation)
VALUES
    (4001, 'Semences et outils agricoles', 'Agri Plus', 'Bamako', 'VALIDE', 1001),
    (4002, 'Engrais et amendements', 'Faso Intrants', 'Sikasso', 'VALIDE', 1002),
    (4003, 'Materiel agricole', 'Equip Mali', 'Kayes', 'EN_ATTENTE', NULL);

-- 3. Tables dependantes des utilisateurs et categories
INSERT INTO partenaire_financiers
    (id_partenaire, nom_partenaire, typepartenaire, id_administrateur)
VALUES
    (1, 'Banque Agricole du Mali', 'BANQUE', 1001),
    (2, 'Microfinance Sugu', 'MICROFINANCE', 1002),
    (3, 'Initiative Sahel', 'ONG', 1003);

INSERT INTO prestataires
    (id_prestataire, nom_prestataire, type_prestataire, description, specialite,
     telephone, localisation, image_url, id_administrateur)
VALUES
    (1, 'Conseil Rural', 'CONSEILLER_AGRICOLE', 'Accompagnement des exploitations', 'Agronomie', '+22371001001', 'Bamako', NULL, 1001),
    (2, 'Techni Champ', 'TECHNICIEN_AGRICOLE', 'Maintenance de materiel', 'Mecanique agricole', '+22371001002', 'Sikasso', NULL, 1002),
    (3, 'Agro Conseil Segou', 'CONSEILLER_AGRICOLE', 'Conseil aux producteurs', 'Irrigation', '+22371001003', 'Segou', NULL, 1003);

INSERT INTO cultures (id_culture, nom_culture, description, id_categorie)
VALUES
    (1, 'Mil', 'Culture adaptee aux zones seches', 1),
    (2, 'Mais', 'Culture cerealieres de saison des pluies', 1),
    (3, 'Niebe', 'Legumineuse a cycle court', 2);

INSERT INTO commandes (id_commande, date_commande, status, montant_total, id_utilisateur)
VALUES
    (1, '2026-02-01 10:00:00', 'EN_COURS', 25000, 2001),
    (2, '2026-02-02 11:30:00', 'LIVREE', 18000, 2002),
    (3, '2026-02-03 08:15:00', 'EN_COURS', 32000, 2003);

INSERT INTO notifications (id_notification, titre, message, type, date_envoi, lue, id_utilisateur)
VALUES
    (1, 'Nouveau conseil', 'Un conseil agricole est disponible.', 'CONSEIL', '2026-02-04 09:00:00', 0, 2001),
    (2, 'Commande livree', 'Votre commande a ete livree.', 'COMMANDE', '2026-02-04 10:00:00', 1, 2002),
    (3, 'Prix du marche', 'Les prix du marche ont ete actualises.', 'PRIX_MARCHE', '2026-02-04 11:00:00', 0, 2003);

INSERT INTO prix_marches
    (id_prix, nom_produit, prix_produit, unite, localisation, date_observ, id_administrateur)
VALUES
    (1, 'Mil', 300, 'kg', 'Bamako', '2026-02-05 08:00:00', 1001),
    (2, 'Mais', 275, 'kg', 'Sikasso', '2026-02-05 08:30:00', 1002),
    (3, 'Riz local', 500, 'kg', 'Segou', '2026-02-05 09:00:00', 1003);

-- 4. Ressources dependantes des partenaires, prestataires, fournisseurs,
--    agriculteurs, categories, cultures et administrateurs
INSERT INTO financements
    (id_financement, type_financement, condition_principales, montant_min,
     montant_max, image_url, disponible, id_partenaire, id_administrateur)
VALUES
    (1, 'Credit de campagne', 'Etre agriculteur en activite', 100000, 1000000, NULL, 1, 1, 1001),
    (2, 'Microcredit semences', 'Dossier simplifie', 50000, 300000, NULL, 1, 2, 1002),
    (3, 'Subvention equipement', 'Projet agricole valide', 200000, 2000000, NULL, 1, 3, 1003);

INSERT INTO service_agricole
    (id_service, type_service, description, cout_estime, image_url, disponible,
     id_prestataire, id_administrateur)
VALUES
    (1, 'Diagnostic de sol', 'Analyse et recommandations', '15000 FCFA', NULL, 1, 1, 1001),
    (2, 'Reparation de pompe', 'Entretien de pompe a eau', '25000 FCFA', NULL, 1, 2, 1002),
    (3, 'Conseil irrigation', 'Optimisation de l arrosage', '10000 FCFA', NULL, 1, 3, 1003);

INSERT INTO tutoriels
    (id_tutoriel, titre_tutoriel, description_tutoriel, url_image, url_video,
     id_administrateur, id_categorie)
VALUES
    (1, 'Cultiver le mil', 'Etapes de preparation et de semis', 'https://example.org/mil.jpg', 'https://example.org/mil.mp4', 1001, 1),
    (2, 'Produire du mais', 'Conseils pour une bonne recolte', 'https://example.org/mais.jpg', 'https://example.org/mais.mp4', 1002, 1),
    (3, 'Reussir le niebe', 'Conduite de culture du niebe', 'https://example.org/niebe.jpg', 'https://example.org/niebe.mp4', 1003, 2);

INSERT INTO conseils
    (id_conseil, titre_conseil, contenu, date_pub, id_culture, id_administrateur)
VALUES
    (1, 'Preparation du sol', 'Preparer le sol avant les premieres pluies.', '2026-02-06', 1, 1001),
    (2, 'Semis du mais', 'Respecter les espacements recommandes.', '2026-02-07', 2, 1002),
    (3, 'Rotation des cultures', 'Alterner cereales et legumineuses.', '2026-02-08', 3, 1003);

INSERT INTO produit_agricoles
    (id_produit, nom_produit, description, quantite, prix, unite, disponible,
     image_url, date_pub, id_agriculteur, id_administrateur)
VALUES
    (1, 'Mil local', 'Mil recolte de la saison', 500, 300, 'kg', 1, NULL, '2026-02-09 09:00:00', 3001, 1001),
    (2, 'Mais jaune', 'Mais sec trie', 350, 275, 'kg', 1, NULL, '2026-02-09 10:00:00', 3002, 1002),
    (3, 'Niebe rouge', 'Niebe propre et conditionne', 120, 650, 'kg', 1, NULL, '2026-02-09 11:00:00', 3003, 1003);

INSERT INTO intrants
    (id_intrant, nom_intrant, type_intrant, description, prix, disponible,
     statut_intrant, image_url, id_fournisseur, id_administrateur_validation)
VALUES
    (1, 'Semences de mil', 'SEMENCE', 'Semences selectionnees', 5000, 1, 'VALIDE', NULL, 4001, 1001),
    (2, 'Engrais organique', 'ENGRAIS', 'Sac de 25 kg', 8000, 1, 'VALIDE', NULL, 4002, 1002),
    (3, 'Pulverisateur manuel', 'MATERIEL', 'Pulverisateur pour petites parcelles', 15000, 1, 'EN_ATTENTE', NULL, 4003, 1003);

-- 5. Associations plusieurs-a-plusieurs explicites
INSERT INTO cultures_agriculteurs (id_culture_agriculteur, id_agriculteur, id_culture)
VALUES
    (1, 3001, 1),
    (2, 3002, 2),
    (3, 3003, 3);

-- 6. Details en dernier, apres commandes, produits et intrants.
-- La table detail_commandes suppose le correctif @Table indique en tete.
INSERT INTO detail_commandes
    (id_detail, quantite, prix_unitaire, montant, id_commande, id_produit, id_intrant)
VALUES
    (1, 10, 300, 3000, 1, 1, NULL),
    (2, 2, 8000, 16000, 2, NULL, 2),
    (3, 5, 650, 3250, 3, 3, NULL);

COMMIT;
