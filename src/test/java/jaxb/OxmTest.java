package jaxb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OxmTest {
    @Autowired
    Unmarshaller unmarshaller;

    @Test
    public void unmarshallSqlMap() throws IOException {
        Source xmlSource = new StreamSource(
                getClass().getResourceAsStream("sqlmap.xml")
        );
        Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource);
        List<SqlType> sqlList = sqlmap.getSql();
        assertThat(sqlList.size(), is(3));
        assertThat(sqlList.get(0).getType(), is("add"));
        assertThat(sqlList.get(0).getValue(), is("insert"));
        assertThat(sqlList.get(1).getType(), is("get"));
        assertThat(sqlList.get(1).getValue(), is("select"));
        assertThat(sqlList.get(2).getType(), is("delete"));
        assertThat(sqlList.get(2).getValue(), is("delete"));
    }
}
