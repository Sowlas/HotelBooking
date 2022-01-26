package com.example.agencyrest;


import com.example.agencyrest.hotelRESTmodels.Booking;
import com.example.agencyrest.hotelRESTmodels.Customer;
import com.example.agencyrest.hotelRESTmodels.Offer;
import com.example.agencyrest.models.Agency;
import com.example.agencyrest.repositories.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


@Component
public class Main {
    @Autowired
    RestTemplate proxy;
    String img_path = "IMG/Agency/";
    @Autowired
    private AgencyRepository agencyRepository;

    public static boolean checkDate(String s) {
        if (s.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
            return true;
        }
        return false;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws InterruptedException {
        runCLI();
    }

    public void runCLI() throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        System.out.println("----------------------------------------------------------------");
        System.out.println("Bienvenue dans le programme de consultation et de réservation de chambres d'hôtels.");
        System.out.println(
                "Veuillez selectionner les identifiants de connexion de l'agence à laquelle vous souhaitez vous connecter.");
        List<Agency> agencies = agencyRepository.findAll();
        for (int i = 0; i < agencies.size(); i++) {
            System.out.println("	" + (i + 1) + " - " + agencies.get(i).getName());
        }
        System.out.print("Votre choix: ");
        int ag = sc.nextInt() - 1;
        sc.nextLine();
        Agency agency = agencies.get(ag);
        System.out.print("Entrez le mot de passe: ");
        String password = sc.nextLine();
        while (!password.equals(agency.getPassword())) {
            System.out.println("Mot de passe incorrect.");
            System.out.println(
                    "Veuillez reessayer d'entrer le mot de passe de l'agence à laquelle vous souhaitez vous connecter.");
            password = sc.nextLine();
        }
        System.out.println("Connexion à " + agency.getName() + " effectuée avec succès !");
        System.out.println("\n-------------------------" + agency.getName() + "-------------------------");


        System.out.println("\nBienvenu chez l'agence : " + agency.getName());
        sc.reset();
        while (true) {
            System.out.println("\n-------------------------MENU PRINCIPAL-------------------------");
            System.out.println("   1. Consultation des offres");
            System.out.println("   2. Quitter");
            System.out.println("-------------------------MENU PRINCIPAL-------------------------\n");
            System.out.print("Votre choix : ");

            Scanner choix = new Scanner(System.in);
            String choixUser = choix.nextLine();
            int num = Integer.parseInt(choixUser);
            sc.reset();
            switch (num) {

                case 1:
                    System.out.println("Veuillez renseigner vos critères de préférences. ");

                    Scanner infos = new Scanner(System.in);
                    System.out.println("Ville de séjour: (Montpellier, Paris, Nice, Marseille, Lyon)");
                    String ville = infos.nextLine();

                    System.out.println("Date de début de séjour: (Format AAAA-MM-JJ)");
                    String dateDebs = infos.nextLine();
                    int startDate;
                    if (!checkDate(dateDebs)) {
                        startDate = Integer.MAX_VALUE;
                    } else startDate = Integer.parseInt(dateDebs.replace("-", ""));
                    while (!checkDate(dateDebs)) {
                        System.out.println("Veuillez saisir une date au format correct (Format AAAA-MM-JJ)");
                        dateDebs = infos.nextLine();
                        if (checkDate(dateDebs)) {
                            startDate = Integer.parseInt(dateDebs.replace("-", ""));
                        }
                        ;
                    }

                    System.out.println("Date de fin de séjour: (Format AAAA-MM-JJ)");
                    String dateFing = infos.nextLine();
                    int endDate;
                    if (!checkDate(dateFing)) {
                        endDate = 0;
                    } else endDate = Integer.parseInt(dateFing.replace("-", ""));

                    while ((endDate < startDate) || (!checkDate(dateFing))) {

                        System.out.println("Veuillez entrer une date de fin après votre date de début et dans un format correct (Format AAAA-MM-JJ)");
                        dateFing = infos.nextLine();
                        if (checkDate(dateFing)) {
                            endDate = Integer.parseInt(dateFing.replace("-", ""));
                        }
                    }


                    System.out.println("Nombre de lits: (Seulement des chambres de 1 à 4 lits disponibles)");
                    int nbLits = Integer.parseInt(infos.nextLine());

                    System.out.println("Nombre d'étoiles: (Seulement des hôtels 1 à 5 étoiles disponibles) ");
                    int nbEtoile = Integer.parseInt(infos.nextLine());

                    System.out.println(
                            "Budget minimum par personne par nuit: (Le prix des chambres n'excède pas 450e)");
                    int prixMin = Integer.parseInt(infos.nextLine());

                    System.out.println(
                            "Budget maximum par personne par nuit: (Le prix des chambres n'excède pas 450e)");
                    int prixMax = Integer.parseInt(infos.nextLine());


                    System.out.println(
                            "Merci, nous vous proposerons les chambres disponibles qui correspondent à vos critères ...");
                    Thread.sleep(500);

                    String url = "http://localhost:8081/agency/" + agency.getAgencyId() + "/lookup/city=" + ville + ",nbStars=" + nbEtoile
                            + ",from=" + dateDebs + ",to=" + dateFing + ",minPrice=" + prixMin + ",maxPrice=" + prixMax + ",nbBeds=" + nbLits;

                    Offer[] response = proxy.getForObject(url, Offer[].class);
                    for (int i = 0; i < response.length; i++) {
                        System.out.println(" - " + i + " : " + response[i].toString());
                    }
                    if (response.length == 0) {
                        System.out.println("Aucune offre ne correspond à vos critères.");
                        break;
                    }
                    Scanner sub = new Scanner(System.in);
                    boolean subMenu = true;

                    while (subMenu) {
                        System.out.println("\n-----------------------------------");
                        System.out.println("Voulez-vous consulter une offre ? ");
                        System.out.println("   1. Oui ");
                        System.out.println("   2. Retour au menu principal");
                        System.out.println("-----------------------------------\n");

                        int num2 = Integer.parseInt(sub.nextLine());

                        switch (num2) {
                            case 1:
                                System.out.println("Veuillez entrer le numéro de l'offre à consulter.");
                                int idOffre = Integer.parseInt(infos.nextLine());
                                if (idOffre + 1 > response.length) {
                                    break;
                                }
                                try {
                                    BufferedImage bi = ImageIO.read(response[idOffre].getRoom().getImg());
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    ImageIO.write(bi, "jpg", bos);

                                    byte[] data = bos.toByteArray();
                                    ByteArrayInputStream bis = new ByteArrayInputStream(data);
                                    BufferedImage bImage2 = ImageIO.read(bis);
                                    File file = new File(img_path + response[idOffre].getRoom().getName() + ".jpg");
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                    file.createNewFile();
                                    ImageIO.write(bImage2, "jpg", file);
                                    System.out.println("Image créée : " + file.getAbsolutePath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                System.out.println("Voulez vous réserver cette chambre ? (1 si OUI 2 SINON)");

                                int num3 = Integer.parseInt(infos.nextLine());

                                switch (num3) {
                                    case 1:
                                        System.out.println("Veuillez renseigner votre nom");
                                        String nom = infos.nextLine();
                                        System.out.println("Veuillez renseigner votre prenom");
                                        String prenom = infos.nextLine();
                                        System.out.println("Veuillez renseigner vos coordonnées bancaires");
                                        String coordCB = infos.nextLine();

                                        Customer client = new Customer(nom, prenom, coordCB);
                                        url = "http://localhost:8080/customer";
                                        Customer customer = proxy.postForObject(url, client, Customer.class);


                                        Booking booking = new Booking(response[idOffre].getRoom().getRoomId().intValue(),
                                                customer.getCustomerId().intValue(), dateDebs, dateFing);

                                        url = "http://localhost:8080/booking";
                                        Booking bookingAdd = proxy.postForObject(url, booking, Booking.class);

                                        System.out.println("RESERVATION CONFIRMEE : (" + bookingAdd.getBookingId() + ")");

                                        System.out.println("--------------------RECAP RESERVATION ---------------------------");
                                        System.out.println(booking);
                                        System.out.println("--------------------FIN RECAP RESERVATION ---------------------------");

                                        break;
                                    case 2:
                                        System.out.println("Retour au choix d'une offre");
                                        break;
                                    default:
                                        System.out.println("Veuillez saisir un chiffre entre 1 et 2 ");

                                }
                            case 2:
                                subMenu = false;
                                System.out.println("Retour au menu principal.");
                                break;

                            default:
                                System.out.println("Veuillez saisir un chiffre entre 1 et 2");
                        }
                    }

                    break;

                case 2:
                    System.out.println("Fin du programme.");
                    System.exit(0);
                default:
                    System.out.println("Veuillez saisir un chiffre entre 1 et 2");
            }
        }
    }
}
