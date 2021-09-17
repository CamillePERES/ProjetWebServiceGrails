package projetwebservice

import grails.gorm.services.Service

@Service(Advert)
interface IAdvertService {

    Advert get(Serializable id)

    List<Advert> list(Map args)

    Long count()

    void delete(Serializable id)

    Advert save(Advert advert)

}