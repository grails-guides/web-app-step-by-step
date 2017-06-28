package ice.cream

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@Transactional
class UserService {

    User createUser(String username, String password) {
        User user = new User(username: username, password: password).save()
        Role role = Role.findByAuthority("ROLE_USER")

        UserRole.create(user, role, true)

        user
    }

    @ReadOnly
    boolean existsUserByUsername(String name) {
        User.where { username == name }.count()
    }
}
