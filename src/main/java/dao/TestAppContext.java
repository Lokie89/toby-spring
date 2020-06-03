package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;

@Configuration
@Profile("test")
public class TestAppContext {

    @Bean
    public UserService testUserService() {
        return new TestUserService();
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        GeneralUserLevelUpgradePolicy generalUserLevelUpgradePolicy = new GeneralUserLevelUpgradePolicy();
        generalUserLevelUpgradePolicy.setMailSender(mailSender());
        return generalUserLevelUpgradePolicy;
    }
}
