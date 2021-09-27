package projetapigrails

import grails.gorm.transactions.Transactional

@Transactional
class CUserRoleService {

    UserService userService

    Set<Role> addRoleToUser(UserRoleAssignment ura) {

        User user = userService.getUserById(ura.idUser);
        Role role = Role.get(ura.idRole);
        UserRole.create(user, role, true)
        return user.getAuthorities();
    }

    def deleteRoleOfUser(Long userId, Long roleId) {

        //ramene le role qui a pour id roleId
        Role r = Role.get(roleId)

        //ramene l'user actuellement connecte
        User connected = userService.getLoggedInstanceUser();

        //si le role a supprimer est admin ET que j'essaye de me supprimer un role (je sais que c'est l'admin qui est deja co)
        if(r != null && connected != null && r.authority == "ROLE_ADMIN" && connected.id == userId)
            return

        //sinon supprimer le role
        UserRole.criteriaFor(userId, roleId).deleteAll()
    }

    def roleByUser (Long userId, Long roleId){


    }
}
