package dao;

import domain.User;

public class TestUserService extends UserService {

    private String id;

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
