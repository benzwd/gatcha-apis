package com.project.player_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service qui gère le profil des joueurs.
 */
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Récupère le profil du joueur.
     *
     * @param username nom d'utilisateur.
     * @return profil du joueur.
     */
    public Player getProfile(String username) {
        return playerRepository.findById(username)
                .orElseGet(() -> createDefaultPlayer(username));
    }

    /**
     * Ajoute de l'expérience au joueur et vérifie la montée en niveau.
     *
     * @param username nom d'utilisateur.
     * @param xp quantité d'expérience à ajouter.
     * @return profil mis à jour.
     */
    public Player addExperience(String username, double xp) {
        Player player = getProfile(username);
        player.setExperience(player.getExperience() + xp);
        if (player.getExperience() >= player.getExperienceThreshold()) {
            levelUp(player);
        }
        return playerRepository.save(player);
    }

    /**
     * Procède à la montée en niveau du joueur.
     *
     * @param username nom d'utilisateur.
     * @return profil mis à jour.
     */
    public Player levelUp(String username) {
        Player player = getProfile(username);
        levelUp(player);
        return playerRepository.save(player);
    }

    /**
     *
     * @param player joueur à faire monter de niveau.
     */
    private void levelUp(Player player) {
        player.setLevel(player.getLevel() + 1);
        player.setExperience(0);
        player.setExperienceThreshold(player.getExperienceThreshold() * 1.1);
        // On pourrait ici augmenter la capacité maximale de la collection de monstres.
    }

    /**
     * Ajoute un nouveau monstre à la collection du joueur.
     *
     * @param username nom d'utilisateur.
     * @param monsterId identifiant du monstre.
     * @return profil mis à jour.
     */
    public Player addMonster(String username, String monsterId) {
        Player player = getProfile(username);
        player.getMonsterIds().add(monsterId);
        return playerRepository.save(player);
    }

    /**
     * Supprime un monstre de la collection du joueur.
     *
     * @param username nom d'utilisateur.
     * @param monsterId identifiant du monstre à supprimer.
     * @return profil mis à jour.
     */
    public Player removeMonster(String username, String monsterId) {
        Player player = getProfile(username);
        player.getMonsterIds().remove(monsterId);
        return playerRepository.save(player);
    }

    /**
     * Crée un profil joueur par défaut.
     *
     * @param username nom d'utilisateur.
     * @return nouveau joueur.
     */
    private Player createDefaultPlayer(String username) {
        Player player = new Player();
        player.setUsername(username);
        player.setLevel(1);
        player.setExperience(0);
        player.setExperienceThreshold(50); // seuil de départ
        return playerRepository.save(player);
    }
}
