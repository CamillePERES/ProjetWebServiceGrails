package projetapigrails

class Picture {

    String filename;

    static belongsTo = [advert: Advert];
    static constraints = {
    }
}
