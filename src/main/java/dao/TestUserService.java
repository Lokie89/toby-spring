package dao;

import domain.User;

import java.util.List;

public class TestUserService extends UserServiceImpl {

    private String id = "madnite1";

    public TestUserService() {

    }

    public TestUserService(String id) {
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

    public List<User> getAll() {
        for (User user : super.getAll()) {
            super.update(user);
        }
        return null;
    }
}
