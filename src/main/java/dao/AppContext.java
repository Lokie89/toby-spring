package dao;

import com.mysql.cj.jdbc.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "dao")
@EnableSqlService
@PropertySource("/dao/database.properties")
public class AppContext {

    @Value("${db.driverClass}")
    Class<? extends Driver> driverClass;

    @Value("${db.url}")
    String url;

    @Value("${db.username}")
    String username;

    @Value("${db.password}")
    String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public SqlMapConfig sqlMapConfig(){
        return new UserSqlMapConfig();
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(this.driverClass);
        ds.setUrl(this.url);
        ds.setUsername(this.username);
        ds.setPassword(this.password);
        return ds;
    }

    @Configuration
    @Profile("production")
    public static class ProductionAppContext {
        @Bean
        public MailSender mailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("mail.mycompany.com");
            return mailSender;
        }
    }

    @Configuration
    @Profile("test")
    public static class TestAppContext {
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

}
