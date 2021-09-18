package projetapigrails

import grails.validation.Validateable

class UserRoleAssignment implements Validateable{

    Long idUser;
    Long idRole;

    static constraints = {
        idRole nullable: false
        idUser nullable: false
    }
}
