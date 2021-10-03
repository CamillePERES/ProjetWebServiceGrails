package projetapigrails

import grails.validation.Validateable

class UserView implements Validateable{

    /***
     * Ici, je recree un user qui a pour attribut :
     * un user (username, password)
     * une liste de roles
     */
    User user;
    List<Role> roles;
}
