package projetapigrails

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

@ReadOnly
@Secured(['ROLE_ADMIN', 'ROLE_MODERATOR'])
class PictureController {

    PictureService pictureService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 100, 300)
        respond Picture.list(params), model:[pictureCount: Picture.count()]
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def show(Long id) {
        Picture p = pictureService.getPictureById(id)
        //render : renvoie un resultat
        //ici render renvoie les bytes d'un fichier (ici une image) et son extension (contenttype)
        render file: p.bytes, contentType: p.contentType
    }

    @Transactional
    def save(AdvertPictureUpload file) {
        if (file == null) {
            render status: NOT_FOUND
            return
        }
        //hasError = verifie les contraintes de la classe AdvertPictureUpload
        if (file.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond file.errors
            return
        }

        try {
            Picture p = pictureService.addPictureToAdvert(file)
            respond p, status: CREATED
            return
        } catch (ValidationException e) {
            respond file.errors
            return
        }

    }

    /*@Transactional
    def update(Picture picture) {
        if (picture == null) {
            render status: NOT_FOUND
            return
        }
        if (picture.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond picture.errors
            return
        }

        try {
            pictureService.updatePicture(picture)
        } catch (ValidationException e) {
            respond picture.errors
            return
        }

        respond picture, [status: OK, view:"show"]
    }*/

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }
        pictureService.deletePictureById(id);
        render status: NO_CONTENT
    }

}
