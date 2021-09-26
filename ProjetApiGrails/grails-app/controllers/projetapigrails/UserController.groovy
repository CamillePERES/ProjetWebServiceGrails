package projetapigrails

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@ReadOnly
@Secured(value=["hasRole('ROLE_ADMIN')"])
class UserController {

    UserService userService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //nbr d'utilisateurs au max je veux recup
        //params.max = max < 0 ? 0 : max;
        params.max = Math.min(max ?: 10, 100)
        //la position dans le tableau (offset(10), je recup de l'user 10 à 20)
        //params.offset = offset < 0 ? 0 : offset;
        List<User> users = User.list(params);
        respond users, model:[userCount: User.count()]
    }

    def show(Long id) {
        User u = userService.getUserById(id);
        //respond userService.getUserById(id);
        respond u;
    }

    def showUserLogged(){
        respond userService.getLoggedInstanceUser()
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
    def updateUserPassword(User user) {
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
            userService.updateUserPassword(user)
        } catch (ValidationException e) {
            respond user.errors
            return
        }

        respond user, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }
        userService.deleteUserById(id);
        render status: NO_CONTENT;

    }
}
