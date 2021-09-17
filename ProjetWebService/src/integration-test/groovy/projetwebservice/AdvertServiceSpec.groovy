package projetwebservice

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class AdvertServiceSpec extends Specification {

    IAdvertService advertService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Advert(...).save(flush: true, failOnError: true)
        //new Advert(...).save(flush: true, failOnError: true)
        //Advert advert = new Advert(...).save(flush: true, failOnError: true)
        //new Advert(...).save(flush: true, failOnError: true)
        //new Advert(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //advert.id
    }

    void "test get"() {
        setupData()

        expect:
        advertService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Advert> advertList = advertService.list(max: 2, offset: 2)

        then:
        advertList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        advertService.count() == 5
    }

    void "test delete"() {
        Long advertId = setupData()

        expect:
        advertService.count() == 5

        when:
        advertService.delete(advertId)
        sessionFactory.currentSession.flush()

        then:
        advertService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Advert advert = new Advert()
        advertService.save(advert)

        then:
        advert.id != null
    }
}
