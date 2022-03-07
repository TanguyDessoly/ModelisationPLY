# Membres de l'équipe
- BIMONT Maxime
- DESSOLY Tanguy
- MAGRIT Gatien
- VARNIERE Korantin

--------------------------------------------------------------------------------------------------------------

# Livrable 1

## Fonctionnalités implémentées

- [x] Afficher la liste détaillée des modèles
- [x] Il est possible de choisir le modèle à visualiser dans la liste
- [X] Le modèle est chargé et visualisé
- [X] La visualisation est correcte
- [X] On visualise simultanément trois vues du modèle (de face, de haut, de côté)
- [X] Rotation
- [x] Translation
- [x] Homotétie

## Autres éléments demandés

- [X] Tests pour les classes de calcul mathématique
- [x] Captures d'écran pour le rendu
- [x] Vidéo de présentation du rendu
- [x] Respect du format de rendu (cf Moodle)

## Distribution du travail (qui a fait quoi)
Même si chacun avait son rôle dans l’équipe, c’est à base d’entraide que ce projet s’est réalisé, chacun aidant les autres sur leurs tâches, c’est pourquoi la liste suivante ne veut pas dire que seule la personne a développé de A à Z sa partie, mais en est l’acteur principal
 
- BIMONT Maxime
-- Responsable interfaces et documentation 

- DESSOLY Tanguy
-- Créateur de l’explorateur, des bases du moteur et des vues

- MAGRIT Gatien
-- Chargé couleurs et dimensions

- VARNIERE Korantin
-- Chargé des matrices et de la base de lecture des fichier

## Difficultés rencontrées
-Le comparateur de Z qui ne veut pas fonctionner comme les autres

-Scene Builder, vraiment très compliqué à utiliser, interface qui n'a pas de répondant, les images font tout planter etc...

-L'optimisation, toujours pas de vrai réglage à ce jour, dès que l'on dépasse les 5000 faces, le programme est lent, pourtant l'on multiplie et affiche tout ce qu'il y a de plus simple.

-Le jar exécutable avec mvn package qui ne fonctionne pas
--------------------------------------------------------------------------------------------------------------

# Livrable 2

## Fonctionnalités implémentées


- [x] Message d'erreur en cas d'erreur de format dans le fichier
- [x] Affichage faces seulement / segments seulement
- [x] Affichage avancé de la bibliothèque de modèles
- [ ] Recherche dans la bibliothèque de modèles
- [ ] Éditer les informations sur un modèle
- [x] Modèle centré
- [x] Éclairage
- [ ] Lissage
- [ ] Ombre portée
- [ ] Vue en tranches
- [x] Controleur horloge
- [x] Autres, préciser
- [x] Drag & Drop de fichiers
- [x] Choix des couleurs
## Autres exigences

- [x] Tests unitaires
- [x] Diagramme de classes UML
- [x] Javadoc
- [x] Captures d'écran
- [x] Vidéo de présentation
- [x] Respect du format de rendu

## Distribution du travail (qui a fait quoi)
Comme pour le livrable 1 c’est à base d’entraide que ce projet s’est réalisé, chacun aidant les autres sur leurs tâches, c’est pourquoi la liste suivante ne veut pas dire que seule la personne a développé de A à Z sa partie, mais en est l’acteur principal

- BIMONT Maxime
-- S'est principalement occupé de l'interface, de la javadoc et des test unitaires.

- DESSOLY Tanguy
-- S'est principalement occupé de la création de l'explorateur de fichier ply, de la base du moteur 3d et de l'affichage des vues.

- MAGRIT Gatien
-- S'est principalement occupé de la couleur des modèles ply, des dimensions du ply et il a en partie réalisé la lecture du fichier ply

- VARNIERE Korantin
-- S'est principalement occupé de la création et de la modification de matrices, il a en partie réalisé la lecture du fichier ply. Il a également réalisé le controleur horloge et la lumière.

## Difficultés rencontrées

- Scene Builder n'est pas très puissant et il y a une impossiblité de faire responsive

- Difficulté a comprendre le lissage et la vue en tranches

- Classes dépassant vite les 200 lignes

## Utilisation du programme

Dès que le programme se lance on a la possibilité de choisir un modèle ply se trouvant dans la bibliotèque, on peut les trier selon plusieurs critères. Une fois un modèle chargé on peut effectuer des transformations dessus(rotation,translation,homothétie), on peut aussi afficher sur les faces et les vertexs les couleurs que l'on souhaite, utiliser une source lumineuse, un controleur horloge, et choisir d'afficher seulement les faces, les vertexs ou les deux.

il y a des raccourci clavier pour les transformations:
- z pour haut
- s pour bas
- q pour gauche
- d pour droite
- j,l pour faire les rotations autour de X
- i,k pour faire les rotations autour de Y
- u,o pour faire les rotations autour de Z
- \+ pour agrandir
- \- pour rétrécir
- t pour les faces 
- g pour les vertex
- b pour les couleurs
- h pour l'horloge