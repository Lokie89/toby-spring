package dao;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.either;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("junit.xml")
public class JUnitTest {

    static JUnitTest testObject;


    @Test
    public void test1() {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

    @Test
    public void test2() {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

    @Test
    public void test3() {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }


    static Set<JUnitTest> testObjects = new HashSet<>();

    @Test
    public void test4() {
        assertThat(testObjects, not(CoreMatchers.hasItem(this)));
        testObject = this;
    }

    @Test
    public void test5() {
        assertThat(testObjects, not(CoreMatchers.hasItem(this)));
        testObject = this;
    }

    @Test
    public void test6() {
        assertThat(testObjects, not(CoreMatchers.hasItem(this)));
        testObject = this;
    }

    @Autowired
    ApplicationContext context;
    static ApplicationContext contextObject = null;

    @Test
    public void test7() {
        assertThat(testObjects, not(CoreMatchers.hasItem(this)));
        testObject = this;

        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    public void test8() {
        assertThat(testObjects, not(CoreMatchers.hasItem(this)));
        testObject = this;

        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    public void test9() {
        assertThat(testObjects, not(CoreMatchers.hasItem(this)));
        testObject = this;

        assertThat(contextObject, either(is(nullValue())).or(is(this.context)));
        contextObject = this.context;
    }
}
