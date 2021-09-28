package projetapigrails

import grails.validation.Validateable

class Search implements Validateable{

    int max;
    int offset;

    static constraints = {
        max min: 0
        offset min: 0
    }

}
