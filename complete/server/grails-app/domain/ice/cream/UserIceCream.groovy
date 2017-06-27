package ice.cream

class UserIceCream {

    User user
    IceCream iceCream

    static UserIceCream create(User user, IceCream iceCream, boolean flush = false) { //<1>
        def instance = new UserIceCream(user: user, iceCream: iceCream)
        instance.save(flush: flush) //<2>
        instance
    }
}
