package ice.cream

class UserIceCream {

    User user
    IceCream iceCream

    static UserIceCream create(User user, IceCream iceCream, boolean flush = false) {
        def instance = new UserIceCream(user: user, iceCream: iceCream)
        instance.save(flush: flush)
        instance
    }
}
