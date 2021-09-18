package projetapigrails

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.springframework.beans.factory.annotation.Autowired

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
@Secured(value=["hasRole('ROLE_ADMIN')"])
class UserRoleController {

    @Autowired
    CUserRoleService cUserRoleService
    UserRoleService iUserRoleService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",deleteRoleOfUser:"DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UserRole.list(params), model:[userRoleCount: UserRole.count()]
    }

    def show(Long id) {
        respond iUserRoleService.get(id)
    }

    @Transactional
    def save(UserRoleAssignment ura) {
        if (ura == null) {
            render status: NOT_FOUND
            return
        }
        if (ura.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ura.errors
            return
        }

        Set<Role> roles = cUserRoleService.addRoleToUser(ura)

        respond roles, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(UserRole userRole) {
        if (userRole == null) {
            render status: NOT_FOUND
            return
        }
        if (userRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond userRole.errors
            return
        }

        try {
            iUserRoleService.save(userRole)
        } catch (ValidationException e) {
            respond userRole.errors
            return
        }

        respond userRole, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || cUserRoleService.deleteRoleOfUser(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    @Transactional
    def deleteRoleForUser(Long idUser, Long idRole) {
        if (idRole == null || idUser == null) {
            render status: NOT_FOUND
            return
        }
        cUserRoleService.deleteRoleOfUser(idUser, idRole)

        render status: NO_CONTENT
    }
}
