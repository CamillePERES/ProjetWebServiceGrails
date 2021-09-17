package projetwebservice

import grails.gorm.services.Service

@Service(Picture)
interface IPictureService {

    Picture get(Serializable id)

    List<Picture> list(Map args)

    Long count()

    void delete(Serializable id)

    Picture save(Picture picture)

}