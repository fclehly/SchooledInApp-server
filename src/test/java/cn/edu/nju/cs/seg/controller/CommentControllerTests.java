package cn.edu.nju.cs.seg.controller;

import cn.edu.nju.cs.seg.json.JsonMapBuilder;
import cn.edu.nju.cs.seg.pojo.Comment;
import cn.edu.nju.cs.seg.service.CommentService;
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
public class CommentControllerTests {

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
    public void testGetOneCommentSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/comments/" + TEST_COMMENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_COMMENT_ID))
                .andExpect(jsonPath("$.commenter").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.url").exists())
                .andExpect(jsonPath("$.commenter_url").exists())
                .andExpect(jsonPath("$.commenter_avatar_url").exists())
                .andExpect(jsonPath("$.parent_url").exists())
                .andExpect(jsonPath("$.created_at").exists());
    }

    @Test
    public void testGetOneCommentNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostOneComment() throws Exception {
        Comment comment = CommentService.findCommentById(2);
        String requestBody = new JsonMapBuilder()
                .append("commenter_email_or_phone", comment.getUser().getEmail())
                .append("commenter_password", comment.getUser().getPassword())
                .append("content", "content")
                .append("parent_type", "answer")
                .append("parent_id", "1")
                .toString();

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/comments/" + 2)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteOneComment() throws Exception {
        Comment comment = CommentService.findCommentById(2);
        String requestBody = new JsonMapBuilder()
                .append("commenter_email_or_phone", comment.getUser().getEmail())
                .append("commenter_password", comment.getUser().getPassword())
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/comments/" + 2)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }
}
