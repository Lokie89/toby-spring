package dao;

import domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test-applicationContext.xml")
public class UserServiceTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    TestUserService testUserService;

    @Autowired
    UserDao userDao;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    MailSender mailSender;

    @Autowired
    UserLevelUpgradePolicy generalUserLevelUpgradePolicy;

    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, GeneralUserLevelUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER - 1, 0, "email1"),
                new User("joytouch", "강명성", "p2", Level.BASIC, GeneralUserLevelUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER, 0, "email2"),
                new User("erwins", "신승환", "p3", Level.SILVER, 60, GeneralUserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD - 1, "email3"),
                new User("madnite1", "이상호", "p4", Level.SILVER, 60, GeneralUserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD, "email4"),
                new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "email5")
        );
    }

    @Test
    @DirtiesContext
    public void upgradeLevels() {
        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }
        MockMailSender mockMailSender = new MockMailSender();
        GeneralUserLevelUpgradePolicy generalUserLevelUpgradePolicy = new GeneralUserLevelUpgradePolicy();
        generalUserLevelUpgradePolicy.setMailSender(mockMailSender);
        generalUserLevelUpgradePolicy.setUserDao(userDao);
        userService.setUserLevelUpgradePolicy(generalUserLevelUpgradePolicy);

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);

        List<String> request = mockMailSender.getRequests();
        assertThat(request.size(), is(2));
        assertThat(request.get(0), is(users.get(1).getEmail()));
        assertThat(request.get(1), is(users.get(3).getEmail()));

    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevel.getLevel(), is(userWithLevelRead.getLevel()));
        assertThat(userWithoutLevel.getLevel(), is(Level.BASIC));
    }

    @Test
    public void upgradeAllOrNothing() {
        testUserService.setId(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        testUserService.setDataSource(this.dataSource);
        testUserService.setTransactionManager(this.transactionManager);
        testUserService.setUserLevelUpgradePolicy(this.generalUserLevelUpgradePolicy);

        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }
        try {
            testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) {

        }
        checkLevelUpgraded(users.get(1), false);
    }


}
