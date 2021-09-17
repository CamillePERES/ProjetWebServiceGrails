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
        params.max = Math.min(max ?: 10, 100)
        respond pictureService.list(params), model:[pictureCount: pictureService.count()]
    }

    def show(Long id) {
        respond pictureService.getPictureById(id)
    }

    @Transactional
    def save(Picture picture) {
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
            pictureService.addPictureToAdvert(picture)
        } catch (ValidationException e) {
            respond picture.errors
            return
        }

        respond picture, [status: CREATED, view:"show"]
    }

    @Transactional
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
    }

    @Transactional
    def delete(Long id) {
        if (id == null || pictureService.deletePictureById(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
