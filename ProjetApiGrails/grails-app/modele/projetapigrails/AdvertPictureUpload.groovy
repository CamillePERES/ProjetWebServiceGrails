package projetapigrails

import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

//classe qui permet de contenir un fichier
class AdvertPictureUpload implements Validateable{

    MultipartFile featuredImageFile //faire transiter le fichier
    Long idAdvert //id de l'annonce

    static constraints = {
        idAdvert nullable: false
        featuredImageFile  validator: { val, obj ->
            if ( val == null ) {
                return false
            }
            if ( val.empty ) {
                return false
            }

            ['jpeg', 'jpg', 'png'].any { extension ->
                val.originalFilename?.toLowerCase()?.endsWith(extension)
            }
        }
    }

}
