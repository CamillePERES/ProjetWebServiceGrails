package projetwebservice

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(value = ["hasRole('ROLE_ADMIN')"])
class AdvertController {

    AdvertService advertService
    IAdvertService iAdvertService


    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(value = ["hasRole('ROLE_MODERATOR')"])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Advert.list(params), model: [advertCount: Advert.count()]
    }

    @Secured(value = ["hasRole('ROLE_MODERATOR')"])
    def show(Long id) {
        respond advertService.getAdvertById(id)
    }

    def create() {
        respond new Advert(params)
    }

    def save(Advert advert) {
        if (advert == null) {
            notFound()
            return
        }

        try {
            advertService.addAdvertToUser(advert)
        } catch (ValidationException e) {
            respond advert.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'advert.label', default: 'Advert'), advert.id])
                redirect advert
            }
            '*' { respond advert, [status: CREATED] }
        }
    }

    def saveLoggedUser(Advert advert) {
        if (advert == null) {
            notFound()
            return
        }
        try {
            advertService.addAdvertToLoggedUser(advert)
        } catch (ValidationException e) {
            respond advert.errors, view: 'create'
            return
        }
        respond advert, [status: CREATED, view:"show"]
    }

    @Secured(value = ["hasRole('ROLE_MODERATOR')"])
    def edit(Long id) {
            respond advertService.getAdvertById(id)
        }

        @Secured(value = ["hasRole('ROLE_MODERATOR')"])
        def update(Advert advert) {
            if (advert == null) {
                notFound()
                return
            }

            try {
                advertService.updateAdvert(advert)
            } catch (ValidationException e) {
                respond advert.errors, view: 'edit'
                return
            }

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'advert.label', default: 'Advert'), advert.id])
                    redirect advert
                }
                '*' { respond advert, [status: OK] }
            }
        }

        def delete(Long id) {
            if (id == null) {
                notFound()
                return
            }

            advertService.deleteAdvertById(id)

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'advert.label', default: 'Advert'), id])
                    redirect action: "index", method: "GET"
                }
                '*' { render status: NO_CONTENT }
            }
        }

        protected void notFound() {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'advert.label', default: 'Advert'), params.id])
                    redirect action: "index", method: "GET"
                }
                '*' { render status: NOT_FOUND }
            }
        }
    }
