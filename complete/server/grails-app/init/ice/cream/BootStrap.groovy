package ice.cream

class BootStrap {

    def init = { servletContext ->
        println "Loading database..."

        if (!IceCream.count()) {
            def iceCreams = ['vanilla', 'chocolate', 'strawberry'].collect { flavor ->
                new IceCream(flavor: flavor).save(flush: true)
            }

            def ids = iceCreams*.id
            println "Inserted records with ids ${ids.join(',')}"
        }

        if (!Role.count()) {
            def role = new Role(authority: 'ROLE_USER').save(flush: true)
            println "Inserted role..."

            User user = new User(username: 'sherlock', password: 'secret').save(flush: true)
            println "Inserted user..."

            UserRole.create(user, role, true)
            println "Associated user with role..."
        }
    }
    def destroy = {
    }
}
