package cn.edu.nju.cs.seg.controller;

import cn.edu.nju.cs.seg.json.JsonMapBuilder;
import cn.edu.nju.cs.seg.pojo.Answer;
import cn.edu.nju.cs.seg.service.AnswerService;
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
public class AnswerControllerTests {
    private static final int TEST_ANSWER_ID = 1;

    @Autowired
    private AnswerController answerController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(answerController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }


    @Test
    public void testGetOneAnswerSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/answers/" + TEST_ANSWER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ANSWER_ID))
                .andExpect(jsonPath("$.answerer").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.question_title").exists())
                .andExpect(jsonPath("$.comments").exists())
                .andExpect(jsonPath("$.url").exists())
                .andExpect(jsonPath("$.question_url").exists())
                .andExpect(jsonPath("$.answerer_url").exists())
                .andExpect(jsonPath("$.answerer_avatar_url").exists())
                .andExpect(jsonPath("$.comments_url").exists())
                .andExpect(jsonPath("$.created_at").exists())
        ;
    }

    @Test
    public void testGetOneAnswerNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOneAnswer() throws Exception {
        Answer answer = AnswerService.findAnswerById(2);
        String requestBody = new JsonMapBuilder()
                .append("answerer_email_or_phone", answer.getAnswerer().getEmail())
                .append("answerer_password", answer.getAnswerer().getPassword())
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/answers/" + 2)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

}
