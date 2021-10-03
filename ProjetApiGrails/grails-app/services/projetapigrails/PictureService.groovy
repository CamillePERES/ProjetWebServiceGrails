package projetapigrails

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

    /***
     * Prend un objet qui contient un fichier et la transforme en une image d'annonce et l'ajoute à cette même annonce
     * via l'id de l'annonce
     * @param pic
     * @return
     */
    def addPictureToAdvert(AdvertPictureUpload pic) {
        //this.addPictureToAdvertById(pic,pic.advert.id);
        Advert ad = advertService.getAdvertLockById(pic.idAdvert);
        if (ad != null) {
            //Constructeur Picture
            Picture picture = new Picture (
                    filename: pic.featuredImageFile.getOriginalFilename(),
                    bytes: pic.featuredImageFile.getBytes(),
                    contentType: pic.featuredImageFile.getContentType()
            )
            ad.addToPictures(picture);
            ad.save(flush:true);
            return picture;
        }
        return null;
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
