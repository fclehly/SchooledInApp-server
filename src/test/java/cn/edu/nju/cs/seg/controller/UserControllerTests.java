package cn.edu.nju.cs.seg.controller;

import cn.edu.nju.cs.seg.json.JsonMapBuilder;
import cn.edu.nju.cs.seg.pojo.Studio;
import cn.edu.nju.cs.seg.pojo.User;
import cn.edu.nju.cs.seg.pojo.VerificationCode;
import cn.edu.nju.cs.seg.service.StudioService;
import cn.edu.nju.cs.seg.service.UserService;
import cn.edu.nju.cs.seg.service.VerificationCodeService;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by fwz on 2017/7/5.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/mvc-test-config.xml"})
@WebAppConfiguration
public class UserControllerTests {

    private static final int TEST_USER_ID = 1;

    @Autowired
    private UserController userController;

    @Autowired
    private VerificationCodeService verificationCodeService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }

    @Test
    public void testGetUsersSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testGetOneUserSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/" + TEST_USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_USER_ID))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.answers").exists())
                .andExpect(jsonPath("$.questions").exists())
                .andExpect(jsonPath("$.favorite_answers").exists())
                .andExpect(jsonPath("$.favorite_questions").exists())
                .andExpect(jsonPath("$.favorite_essays").exists())
                .andExpect(jsonPath("$.studios").exists())
                .andExpect(jsonPath("$.url").exists())
                .andExpect(jsonPath("$.avatar_url").exists())
                .andExpect(jsonPath("$.questions_url").exists())
                .andExpect(jsonPath("$.answers_url").exists())
                .andExpect(jsonPath("$.favorite_questions_url").exists())
                .andExpect(jsonPath("$.favorite_answers_url").exists())
                .andExpect(jsonPath("$.favorite_essays_url").exists())
                .andExpect(jsonPath("$.studios_url").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.phone").exists())
                .andExpect(jsonPath("$.bio").exists())
                .andExpect(jsonPath("$.sex").exists())
                .andExpect(jsonPath("$.age").exists())
                .andExpect(jsonPath("$.department").exists())
                .andExpect(jsonPath("$.location").exists());
    }

    @Test
    public void testGetOneUserQuestionsSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/"+ TEST_USER_ID + "/questions"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOneUserAnswersSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/"+ TEST_USER_ID + "/answers"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOneUserStudiosSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/"+ TEST_USER_ID + "/studios"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOneUserFavoriteQuestionsSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/"+ TEST_USER_ID + "/favorites/questions"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOneUserFavoriteAnswersSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/"+ TEST_USER_ID + "/favorites/answers"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOneUserFavoriteEssaysSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/"+ TEST_USER_ID + "/favorites/essays"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOneUserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostOneUserSuccess() throws Exception {
        Random random = new Random();
        String testEmail = "";
        for (int i = 0; i < 8; i++) {
            testEmail += random.nextInt(10);
        }
        testEmail += "@nju.edu.cn";
        VerificationCode code = new VerificationCode(
                testEmail, "111111",
                System.currentTimeMillis() + 6 * 60 * 1000);
        this.verificationCodeService.add(code);
        String requestBody = new JsonMapBuilder()
                .append("email", testEmail)
                .append("password", "123456")
                .append("verification_code", code.getCode())
                .toString();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").exists())
                .andReturn();
        String url = JsonPath.read(result.getResponse().getContentAsString(), "$.url");
        int id = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    public void testPostOneUserUnauthorized() throws Exception {
        String requestBody = new JsonMapBuilder()
                .append("email", "TestUser@nju.edu.cn")
                .append("password", "123456")
                .append("verification_code", "0000000")
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostOneQuestion() throws Exception {
        Studio studio = StudioService.findStudioById(1);
        User user = UserService.findUserById(TEST_USER_ID);
        String requestBody = new JsonMapBuilder()
                .append("question_title", "title")
                .append("password", user.getPassword())
                .append("directed_to", studio.getName())
                .toString();

        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();

        InputStream inputStream =
                this.getClass().getResourceAsStream("/images/ic_launcher.png");
//        FileInputStream fis = new FileInputStream("/images/ic_laucher.png");
        System.out.println(inputStream.toString());
        FileInputStream fis = (FileInputStream) inputStream;
//        MockMultipartFile multipartFile =
//                new MockMultipartFile("photopath", "/images/ic_laucher.png",
//                        "image/png", fis);
//        request.addFile(multipartFile);
//        request.setMethod("POST");
//        request.setContentType("multipart/form-data");
//        request.addHeader("Content-type", "multipart/form-data");
//        request.setRequestURI("/api/users/" + TEST_USER_ID + "/questions");
//        request.addParameter("description", requestBody);

//        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
//                .post("/api/users/" + TEST_USER_ID + "/questions")
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("url").exists())
//                .andReturn();
//        String url = JsonPath.read(result.getResponse().getContentAsString(), "$.url");
//        int id = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1));
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/questions/" + id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id));

    }

    @Test
    public void testPostOneFavoriteQuestion() throws Exception {
        User user = UserService.findUserById(TEST_USER_ID);
        String requestBody = new JsonMapBuilder()
                .append("question_id", 1)
                .append("password", user.getPassword())
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/users/" + TEST_USER_ID + "/favorites/questions")
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostOneFavoriteAnswer() throws Exception {
        User user = UserService.findUserById(TEST_USER_ID);
        String requestBody = new JsonMapBuilder()
                .append("answer_id", 1)
                .append("password", user.getPassword())
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/users/" + TEST_USER_ID + "/favorites/answers")
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostOneFavoriteEssay() throws Exception {
        User user = UserService.findUserById(TEST_USER_ID);
        String requestBody = new JsonMapBuilder()
                .append("essay_id", 1)
                .append("password", user.getPassword())
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/users/" + TEST_USER_ID + "/favorites/essays")
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPutOneUser() throws Exception {
        User user = UserService.findUserById(TEST_USER_ID);
        String requestBody = new JsonMapBuilder()
                .append("name", "test")
                .append("old_password", user.getPassword())
                .append("new_password", user.getPassword())
                .append("bio", "bio")
                .append("age", user.getAge() + 1)
                .append("sex", "unknown")
                .append("department", "cs")
                .append("location", "shanghai")
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/api/users/" + TEST_USER_ID + "/favorites/essays")
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/" + TEST_USER_ID))
                .andExpect(jsonPath("name").value("test"))
                .andExpect(jsonPath("bio").value("bio"))
                .andExpect(jsonPath("age").value(user.getAge() + 1))
                .andExpect(jsonPath("sex").value("unknown"))
                .andExpect(jsonPath("department").value("cs"))
                .andExpect(jsonPath("location").value("shanghai"));
    }

    @Test
    public void testDeleteOneFavoriteQuestions() throws Exception {
        User user = UserService.findUserById(TEST_USER_ID);
        UserService.addFavoriteQuestion(TEST_USER_ID, 1);
        String requestBody = new JsonMapBuilder()
                .append("password", user.getPassword())
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/users/" + TEST_USER_ID + "/favorites/questions/1")
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteOneFavoriteAnswers() throws Exception {
        User user = UserService.findUserById(TEST_USER_ID);
        UserService.addFavoriteAnswer(TEST_USER_ID, 1);
        String requestBody = new JsonMapBuilder()
                .append("password", user.getPassword())
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/users/" + TEST_USER_ID + "/favorites/answers/1")
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteOneFavoriteEssays() throws Exception {
        User user = UserService.findUserById(TEST_USER_ID);
        UserService.addFavoriteEssays(TEST_USER_ID, 1);
        String requestBody = new JsonMapBuilder()
                .append("password", user.getPassword())
                .toString();
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/users/" + TEST_USER_ID + "/favorites/essays/1")
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }



}
