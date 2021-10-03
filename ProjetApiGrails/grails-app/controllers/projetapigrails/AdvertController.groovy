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
    def search(Search s) {
        params.max = s.max;
        params.offset = s.offset;
        List<Advert> adverts = advertService.getAllAdvertPagination(params);
        respond new SearchResultAdvert(adverts: adverts, total : Advert.count());
    }

    @Secured(['ROLE_ADMIN', 'ROLE_MODERATOR'])
    def show(Long id) {
        Advert a = advertService.getAdvertById(id);
        respond a;
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
            advertService.addAdvertToUser(advert)
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
        if (id == null ) {
            render status: NOT_FOUND
            return
        }

        advertService.deleteAdvertById(id);
        render status: NO_CONTENT
    }

    def advertPerUser(Long id) {
        respond advertService.getAdvertsByUserId(id);
    }
}
