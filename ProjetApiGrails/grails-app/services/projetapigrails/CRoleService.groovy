package projetapigrails

import grails.gorm.transactions.Transactional

@Transactional
class CRoleService {

    UserService userService;

    def getRoleByUserId(Long idUser){
        User u = userService.get;


    }
}
