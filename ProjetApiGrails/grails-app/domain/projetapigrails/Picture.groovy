package projetapigrails

class Picture {

    String filename;
    byte[] stockByte;
    String contentType;

    static belongsTo = [advert: Advert];
    static constraints = {
        stockByte nullable: true
        contentType nullable: true
        filename nullable: false, blank: false
    }

    static mapping = {
        stockByte column: 'featured_image_bytes', sqlType: 'longblob'
    }

}
