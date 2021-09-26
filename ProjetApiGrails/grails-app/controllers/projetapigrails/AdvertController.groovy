package projetapigrails

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

@ReadOnly
@Secured(value=["hasRole('ROLE_ADMIN')"])
class AdvertController {

    AdvertService advertService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN', 'ROLE_MODERATOR'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Advert.list(params), model:[advertCount: Advert.count()]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_MODERATOR'])
    def show(Long id) {
        respond advertService.getAdvertById(id)
    }

    @Transactional
    def save(Advert advert) {
        if (advert == null) {
            render status: NOT_FOUND
            return
        }
        if (advert.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond advert.errors
            return
        }

        try {
            advertService.addAdvertToLoggedUser(advert)
        } catch (ValidationException e) {
            respond advert.errors
            return
        }

        respond advert, [status: CREATED, view:"show"]
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'ROLE_MODERATOR'])
    def update(Advert advert) {
        if (advert == null) {
            render status: NOT_FOUND
            return
        }
        if (advert.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond advert.errors
            return
        }

        try {
            advertService.updateAdvert(advert)
        } catch (ValidationException e) {
            respond advert.errors
            return
        }

        respond advert, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || advertService.deleteAdvertById(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def advertPerUser(Long id) {
        respond advertService.getAdvertsByUserId(id);
    }
}
