package projetwebservice

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_MODERATOR'])
class PictureController {

    PictureService pictureService
    IPictureService iPictureService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Picture.list(params), model: [pictureCount: Picture.count()]
    }

    def show(Long id) {
        respond pictureService.getPictureById(id)
    }

    def create() {
        respond new Picture(params)
    }

    def save(Picture picture) {
        if (picture == null) {
            notFound()
            return
        }

        try {
            pictureService.addPictureToAdvert(picture)
        } catch (ValidationException e) {
            respond picture.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'picture.label', default: 'Picture'), picture.id])
                redirect picture
            }
            '*' { respond picture, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond pictureService.getPictureById(id)
    }

    def update(Picture picture) {
        if (picture == null) {
            notFound()
            return
        }

        try {
            pictureService.updatePicture(picture)
        } catch (ValidationException e) {
            respond picture.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'picture.label', default: 'Picture'), picture.id])
                redirect picture
            }
            '*' { respond picture, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        pictureService.deletePictureById(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'picture.label', default: 'Picture'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'picture.label', default: 'Picture'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
