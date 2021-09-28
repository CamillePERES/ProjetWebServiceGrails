package projetapigrails


import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired

@Transactional
class UserService {

    @Autowired
    SpringSecurityService security;

    User getLoggedInstanceUser () {
        User user = (User) security.getCurrentUser();
        return user != null ? User.get(user.id) : null;
    }

    User savedUser(User user){
        return user.save();
    }

    User getUserById(Long userId){
        return User.get(userId);
    }

    User getUser(User user) {
        return this.getUserById(user.id);
    }

    List<User> getAllUser(){
        return User.getAll();
    }

    def deleteUserById(Long idUser){
        User user = getUserById(idUser);
        user.delete();
    }

    User deleteUser (User user){
        return this.deleteUserById(user.id)
    }

    def updateUser (User user){
        User us = getUser(user);
        us.username = user.username;
        us.save();
        return us;
    }

    def updateUserPassword (User user){
        User us = getUser(user);
        us.password = user.password;
        us.save();
        return us;
    }

    def getRoleByUser (Long idUser){
        User us = getUserById(idUser);
        return us.getAuthorities();
    }

    //.map peut etre considere comme un forEach
    List<UserView> getAllUserView(Map map){
        List<User> users = User.list(map);
        //collect convertit en liste, peut etre considere comme un asList.
        return users.stream().map{User.userToUserView(it)}.collect();
    }
}
