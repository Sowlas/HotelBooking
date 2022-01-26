package com.example.trivago;

import com.example.trivago.distantRESTmodels.Booking;
import com.example.trivago.distantRESTmodels.Customer;
import com.example.trivago.distantRESTmodels.DistantAgency;
import com.example.trivago.distantRESTmodels.Offer;
import com.example.trivago.models.Agency;
import com.example.trivago.repositories.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Component
public class Main {

    String img_path = "IMG/Trivago/";
    @Autowired
    private RestTemplate proxy;
    @Autowired
    private AgencyRepository agencyRepository;

    public static boolean checkDate(String s) {
        if (s.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
            return true;
        }
        return false;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws Exception {
        runCLI();
    }

    public void runCLI(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------------------------------------------------------------");
        System.out.println("Bienvenue dans le comparateur d'Offres.");
        while (true) {
            System.out.println("\n-------------------------MENU PRINCIPAL-------------------------");
            System.out.println("   1. Afficher agences à comparer");
            System.out.println("   2. Ajouter agence à comparer");
            System.out.println("   3. Supprimer agence à comparer");
            System.out.println("   4. Comparer les Offres");
            System.out.println("   5. Quitter");

            System.out.println("-------------------------MENU PRINCIPAL-------------------------\n");
            System.out.print("Votre choix : ");

            int num = sc.nextInt();
            sc.nextLine();
            Scanner info = new Scanner(System.in);
            switch (num) {

                case 1:

                    List<Agency> agencies = agencyRepository.findAll();

                    if (agencies.size() == 0) {
                        System.out.println("Aucune agence a afficher.");
                    } else {
                        for (Agency a : agencies) {
                            String uri = a.getUri();
                            DistantAgency agence = proxy.getForObject(uri, DistantAgency.class);

                            System.out.println("idAgence: "+ agence.getAgencyId() + " nom Agence: " + agence.getName() +  " mdp: " + agence.getPassword());
                        }
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("URI du web service de l'agence : ");
                    String uri_agence = "";
                    do {
                        uri_agence = info.nextLine();
                    } while (uri_agence.equalsIgnoreCase(""));
                    System.out.println("Recherche de l'agence ....");
                    DistantAgency distantAgency;
                    try {
                        distantAgency = proxy.getForObject(uri_agence, DistantAgency.class);
                        Agency agency = new Agency(null, uri_agence);
                        distantAgency.getAgencyId();
                        if (!agencyRepository.findByUri(uri_agence).isEmpty()) {
                            System.out.println("Agence déjà ajoutée.");
                            break;
                        }
                        agencyRepository.save(agency);
                        System.out.println("L'agence a été ajoutée ! Détail: ");
                        System.out.println(distantAgency.getAgencyId() + " =>  " + distantAgency.getName() + " : " + distantAgency.getPassword());
                    } catch (HttpServerErrorException | IllegalArgumentException e) {
                        System.out.println("Veuillez renseigner une uri correcte.");
                    } catch (NullPointerException e) {
                        System.out.println("Cette agence n'est pas trouvable, impossible de l'ajouter.");
                    }
                    System.out.println();
                    break;

                case 3:
                    System.out.println("Identifiant de l'agence a supprimer : ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    if (agencyRepository.findById(Long.valueOf(id)).isEmpty()) {
                        System.out.println("Agence introuvable");
                        break;
                    }
                    agencyRepository.deleteById(Long.valueOf(id));
                    System.out.println("Agence supprimée.");
                    System.out.println();
                    break;

                case 4:
                    Scanner infos = new Scanner(System.in);
                    System.out.println("Veuillez renseigner vos critères de préférences. ");

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


                    System.out.println("Nombre de lits: (Seulement des chambres de 1 à 4 personnes disponibles)");
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

                    System.out.println();
                    List<Agency> liste_Agencies = agencyRepository.findAll();
                    System.out.println("Recherche d'offres parmi " + liste_Agencies.size() + " agences.");

                    List<Offer> Offers = new ArrayList<>();
                    String url;
                    for (Agency a : liste_Agencies) {
                        url = a.getUri() + "/lookup/city=" + ville + ",nbStars=" + nbEtoile
                                + ",from=" + dateDebs + ",to=" + dateFing + ",minPrice=" + prixMin
                                + ",maxPrice=" + prixMax + ",nbBeds=" + nbLits;

                        DistantAgency ag = proxy.getForObject(a.getUri(), DistantAgency.class);
                        Offer[] off = proxy.getForObject(url, Offer[].class);
                        for (Offer o : off) {
                            o.setDistantAgency(ag);
                            Offers.add(o);
                        }
                    }

                    List<Offer> response = new ArrayList<>();
                    for (Offer o : Offers) {
                        boolean minOffer = true;
                        for (Offer o1 : Offers) {
                            if (o.getRoom().getRoomId().equals(o1.getRoom().getRoomId()) && !o1.equals(o)) {
                                if (o.getPrice() >= o1.getPrice()) {
                                    minOffer = false;
                                }
                            }
                        }
                        if (minOffer) {
                            response.add(o);
                        }
                    }

                    System.out.println("Parmi les " + Offers.size() + "correspondant à vos critères,seulement " + response.size() + " ont été retenues.");
                    for (int i = 0; i < response.size(); i++) {
                        System.out.println(" - " + i + " : " + response.get(i).toString());
                    }
                    if (response.size() == 0) {
                        System.out.println("Rien trouve");
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
                                if (idOffre + 1 > response.size()) {
                                    break;
                                }
                                try {
                                    BufferedImage bi = ImageIO.read(response.get(idOffre).getRoom().getImg());
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    ImageIO.write(bi, "jpg", bos);

                                    byte[] data = bos.toByteArray();
                                    ByteArrayInputStream bis = new ByteArrayInputStream(data);
                                    BufferedImage bImage2 = ImageIO.read(bis);
                                    File file = new File(img_path + response.get(idOffre).getRoom().getName() + ".jpg");
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


                                        Booking booking = new Booking(response.get(idOffre).getRoom().getRoomId().intValue(),
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
                case 5:
                    System.out.println("Fin du programme.");
                    System.exit(0);

                default:
                    System.out.println("Veuillez saisir un chiffre entre 1 et 2");
            }
        }
    }
}







