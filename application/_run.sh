#!/bin/bash

# Charger la configuration
source config.conf

# Définir les chemins
sourceFolder="$root/src/mg/itu/java"
destinationFolder="$root/build"
lib="$root/lib"
src="$root"
lib_jar="$root/web/WEB-INF/lib"

# Créer un dossier temporaire
mkdir -p "$root/temp"

# Copier tous les fichiers .java vers le dossier temporaire
find "$sourceFolder" -type f -name "*.java" -exec cp {} "$root/temp/" \;

# Aller dans le dossier temporaire et compiler les fichiers Java
cd "$root/temp" || exit
javac -d "$destinationFolder" -cp "$lib/*" *.java

# Copier les fichiers compilés vers WEB-INF/classes
cp -r "$root/build"/* "$root/web/WEB-INF/classes/"

# Copier les fichiers de la bibliothèque vers WEB-INF/lib
cp -r "$lib/"* "$lib_jar/"

# Créer le fichier WAR
cd "$src/web" || exit
jar -cvf "../$projet_name.war" .

# Copier le fichier WAR vers webapps
cp "../$projet_name.war" "$serveur/"

# Supprimer le dossier temporaire
rm -rf "$root/temp"

echo "Déploiement terminé."
