package com.makhtout.promptengai.outputs;

public record CinModel(
        String nom,
        String prenom,
        String dateNaissance,
        String arabicFirstName,
        String arabicLastName,
        String dateValidite,
        String numero,
        String lieuNaissance,
        String arabiclieuNaissance
        ) {
}