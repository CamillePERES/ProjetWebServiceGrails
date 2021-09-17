package projetapi

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
@Secured(value=["hasRole('ROLE_ADMIN')"])
class UserController {

    UserService userService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userService.list(params), model:[userCount: userService.count()]
    }

    def show(Long id) {
        respond userService.getUserById(id)
    }

    @Transactional
    def save(User user) {
        if (user == null) {
            render status: NOT_FOUND
            return
        }
        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors
            return
        }

        try {
            userService.savedUser(user)
        } catch (ValidationException e) {
            respond user.errors
            return
        }

        respond user, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(User user) {
        if (user == null) {
            render status: NOT_FOUND
            return
        }
        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors
            return
        }

        try {
            userService.updateUser(user)
        } catch (ValidationException e) {
            respond user.errors
            return
        }

        respond user, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || userService.deleteUserById(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
