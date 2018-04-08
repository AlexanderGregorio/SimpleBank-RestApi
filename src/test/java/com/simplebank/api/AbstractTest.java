package com.simplebank.api;

import com.simplebank.test.UriBuilder;
import com.simplebank.test.factory.EntityFactory;
import com.simplebank.test.parser.Parser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.Assert.fail;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected Parser parser;

    @Autowired
    protected EntityFactory entityFactory;

    @Autowired
    protected UriBuilder uriBuilder;

    @Rule
    public TestWatcher logTestName = new TestWatcher() {

        @Override
        protected void starting(Description description) {
            String className = description.getTestClass().getSimpleName();
            String method = description.getMethodName();
            log.debug(className + " - " + method);
        }
    };

    protected MockHttpServletResponse post(String uri, Object object) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(parser.asString(object)))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse();
        } catch (Exception e) {
            log.error("Failure when making POST.", e);
            fail();
        }

        return null;
    }

    protected MockHttpServletResponse put(String uri, Object object) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.put(uri)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(parser.asString(object)))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse();
        } catch (Exception e) {
            log.error("Failure when making PUT.", e);
            fail();
        }

        return null;
    }

    protected MockHttpServletResponse get(String uri) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.get(uri))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse();
        } catch (Exception e) {
            log.error("Failure when making GET.", e);
            fail();
        }

        return null;
    }

    protected MockHttpServletResponse delete(String uri) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse();
        } catch (Exception e) {
            log.error("Failure when making DELETE.", e);
            fail();
        }

        return null;
    }

}
