package cn.edu.nju.cs.seg.controller;

import cn.edu.nju.cs.seg.json.JsonMapBuilder;
import cn.edu.nju.cs.seg.pojo.Question;
import cn.edu.nju.cs.seg.service.QuestionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by fwz on 2017/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/mvc-test-config.xml"})
@WebAppConfiguration
public class QuestionControllerTests {
    private static final int TEST_QUESTION_ID = 1;

    @Autowired
    private QuestionController questionController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(questionController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }

    @Test
    public void testGetQuestionsSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/questions"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOneQuestionSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/questions/" + TEST_QUESTION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_QUESTION_ID));
    }

    @Test
    public void testGetOneQuestionNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOneQuestion() throws Exception {
        Question question = QuestionService.findQuestionById(2);
        String requestBody = new JsonMapBuilder()
                .append("manager_email_or_phone", question.getStudio().getManager().getEmail())
                .append("manager_password", question.getStudio().getManager().getPassword())
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/questions/" + 2)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }
}
