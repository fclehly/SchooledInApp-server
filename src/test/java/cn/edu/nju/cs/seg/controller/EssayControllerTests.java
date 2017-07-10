package cn.edu.nju.cs.seg.controller;

import cn.edu.nju.cs.seg.json.JsonMapBuilder;
import cn.edu.nju.cs.seg.pojo.Essay;
import cn.edu.nju.cs.seg.service.EssayService;
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
public class EssayControllerTests {
    private static final int TEST_COMMENT_ID = 1;

    @Autowired
    private CommentController commentController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(commentController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }

    @Test
    public void testGetEssaysSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/essays"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOneEssaySuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/essays/" + TEST_COMMENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_COMMENT_ID))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.heat").exists())
                .andExpect(jsonPath("$.supports").exists())
                .andExpect(jsonPath("$.comments").exists())
                .andExpect(jsonPath("$.studio").exists())
                .andExpect(jsonPath("$.studio_bio").exists())
                .andExpect(jsonPath("$.url").exists())
                .andExpect(jsonPath("$.studio_url").exists())
                .andExpect(jsonPath("$.studio_avatar_url").exists())
                .andExpect(jsonPath("$.comments_url").exists())
                .andExpect(jsonPath("$.created_at").exists());
    }

    @Test
    public void testGetOneEssayNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOneEssay() throws Exception {
        Essay essay = EssayService.findEssayById(2);
        String requestBody = new JsonMapBuilder()
                .append("manager_email_or_phone", essay.getStudio().getManager().getEmail())
                .append("manager_password", essay.getStudio().getManager().getPassword())
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/essays/" + 2)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }
}
