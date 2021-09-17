package projetapi


import grails.gorm.transactions.Transactional

@Transactional
class PictureService {

    AdvertService advertService;

    Picture getPictureById(Long idPic) {
        return Picture.get(idPic);
    }

    def addPictureToAdvertById(Picture pic, Long idAdvert) {
        Advert ad = advertService.getAdvertById(idAdvert);
        if (ad != null) {
            ad.addToPictures(pic);
            ad.save();
        }
    }

    //pour mettre l'image dans son annonce (iimage belongs to annonce), je recup l'annonce en base avec son id et je lui ajoute l'image dans l'annonce recuperee
    def addPictureToAdvert(Picture pic) {
        //this.addPictureToAdvertById(pic,pic.advert.id);
        Advert ad = advertService.getAdvertById(pic.advert.id);
        if (ad != null) {
            ad.addToPictures(pic);
            ad.save();
        }
    }

    Picture getPicture(Picture pic) {
        return this.getPictureById(pic.id);
    }

    def deletePictureById(Long idPic){
        Picture picture = getPictureById(idPic);
        picture.delete();
    }

    Picture deletePicture (Picture pic){
       return this.deletePictureById(pic.id);
    }

    def updatePicture(Picture pic) {
        Picture picture = getPicture(pic);
        picture.filename = pic.filename;
        picture.save();
        return picture;
    }
}
