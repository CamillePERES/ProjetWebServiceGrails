package projetapigrails

class Picture {

    String filename;
    byte[] bytes;
    String contentType;

    static belongsTo = [advert: Advert];
    static constraints = {
        bytes nullable: true
        contentType nullable: true
        filename nullable: false, blank: false
    }

    static mapping = {
        bytes column: 'featured_image_bytes', sqlType: 'longblob'
    }

}
