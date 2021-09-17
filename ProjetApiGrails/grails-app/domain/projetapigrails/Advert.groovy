package projetapigrails

class Advert {

    String title;
    String description;
    Float price;


    static hasMany = [pictures: Picture];
    static belongsTo = [user:User];
    static constraints = {
        title nullable: false, blank: false, size: 4..50
        description nullable: false , blank: true
        price nullable: false, min: 0F
        pictures maxSize: 5
    }
}
