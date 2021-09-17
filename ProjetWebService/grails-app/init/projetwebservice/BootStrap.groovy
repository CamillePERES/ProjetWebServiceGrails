package projetwebservice

class BootStrap {

    def init = { servletContext ->
        UserRole.withTransaction {
            def adminRole = new Role(authority: 'ROLE_ADMIN').save()
            def adminUser = new User(username: 'admin', password: 'admin').save()
            UserRole.create(adminUser, adminRole, true)

            def moderatorRole = new Role(authority: 'ROLE_MODERATOR').save()
            def moderatorUser = new User(username: 'modo', password: 'modo').save()
            UserRole.create(moderatorUser, moderatorRole, true)

            def clientRole = new Role(authority: 'ROLE_CLIENT').save()
        }

        User.withTransaction {
            List users = ["Alice", "Bob", "Charlie", "Danny", "Etienne"]
            users.each {
                String username ->
                    def userInstance = new User(username: username, password: "itsasecret")
                    (1..5).each {
                        Integer aIdx ->
                            def advertInstance =
                                    new Advert(title: "title $aIdx",
                                            description: "description",
                                            price: aIdx + 300,
                                    )
                            (1..3).each { Integer pic ->
                                advertInstance.addToPictures(new Picture(filename: "image-$aIdx-" + pic))
                            }
                            userInstance.addToAdverts(advertInstance)
                    }
                    if (!userInstance.save())
                        println "Validation failed, User has not been saved to database"
            }


        }
    }

        def destroy = {
    }
}
