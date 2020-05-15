package dao;

import domain.User;

public class TestUserServiceImpl extends UserServiceImpl {

    private String id = "madnite1";

    public TestUserServiceImpl(){

    }

    public TestUserServiceImpl(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) {
            throw new TestUserServiceException();
        }
        super.upgradeLevel(user);
    }
}
