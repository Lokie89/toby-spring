package dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import updatable.EmbeddedDbSqlRegistry;

import javax.sql.DataSource;
import javax.xml.bind.Unmarshaller;

@Configuration
public class SqlServiceContext implements SqlMapConfig {

    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setSqlRegistry(sqlRegistry());
        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlmap(getSqlMapResource());
        return sqlService;
    }

    @Bean
    public SqlRegistry sqlRegistry() {
        EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
        sqlRegistry.setDataSource(embeddedDatabase());
        return sqlRegistry;
    }

    @Bean
    public DataSource embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setName("embeddedDatabase")
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("/updatable/sqlRegistrySchema.sql")
                .build();
    }

    @Bean
    public Unmarshaller unmarshaller() {
        Jaxb2MarshallerEx marshaller = new Jaxb2MarshallerEx();
        marshaller.setContextPath("jaxb");
        return marshaller.createUnmarshaller();
    }

    @Override
    public Resource getSqlMapResource() {
        return new ClassPathResource("sqlmap.xml", UserDao.class);
    }
}
