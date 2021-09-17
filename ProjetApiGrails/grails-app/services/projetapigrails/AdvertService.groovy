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

    def addAdvertToLoggedUser(Advert advert) {
        User u = userService.getLoggedInstanceUser();
        if (u != null) {
            u.addToAdverts(advert);
            u.save();
        }
        else println("User not logged");
    }

    Advert getAdvertById(Long idAd) {
        return Advert.get(idAd);
    }

    Advert getAdvert(Advert ad) {
        return this.getAdvertById(ad.id);
    }

    List<Advert> getAllAdvert() {
        return Advert.getAll();
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

}
