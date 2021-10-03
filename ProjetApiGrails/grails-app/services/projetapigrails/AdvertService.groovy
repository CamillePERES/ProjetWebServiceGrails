package projetapigrails

import grails.gorm.transactions.Transactional

@Transactional
class AdvertService {

    UserService userService;

    def addAdvertToUser(Advert advert) {
        User u = userService.getUserById(advert.user.id);
        if (u != null) {
            u.addToAdverts(advert);
            u.save();
        }
        else println("User does not exist.");
    }

    //le cas ou l'utilisateur peut gerer ses annonces et en creer une
    def addAdvertToLoggedUser(Advert advert) {
        User u = userService.getLoggedInstanceUser();
        if (u != null) {
            u.addToAdverts(advert);
            u.save();
        }
        else println("User not logged");
    }

    Advert getAdvertById(Long idAd) {
        Advert a = Advert.findById(idAd);
        a.pictures = a.pictures;
        return a;
    }

    Advert getAdvertLockById(Long idAd) {
        return Advert.lock(idAd);
    }

    Advert getAdvert(Advert ad) {
        return this.getAdvertById(ad.id);
    }

    List<Advert> getAllAdvert(){
        return Advert.getAll();
    }

    List<Advert> getAllAdvertPagination(Map map) {
        List<Advert> adverts = Advert.list(map);
        return adverts;
    }

    def deleteAdvertById(Long idAd) {
        Advert advert = getAdvertById(idAd);
        advert.delete();
    }

    Advert deleteAdvert(Advert ad) {
        return this.deleteAdvertById(ad.id);
    }

    Advert updateAdvert(Advert ad) {
        Advert advert = getAdvert(ad);
        advert.title = ad.title;
        advert.description = ad.description;
        advert.price = ad.price;
        advert.save();
        return advert;
    }

    def getAdvertsByUserId(Long idUser){
        User u = userService.getUserById(idUser);
        return Advert.findAllByUser(u);

    }

}
