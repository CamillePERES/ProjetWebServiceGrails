package projetwebservice

import grails.gorm.services.Service

@Service(User)
interface IUserService {

    User get(Serializable id)

    List<User> list(Map args)

    Long count()

    void delete(Serializable id)

    User save(User user)

}