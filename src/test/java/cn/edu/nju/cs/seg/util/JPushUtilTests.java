package cn.edu.nju.cs.seg.util;

import cn.edu.nju.cs.seg.pojo.Answer;
import cn.edu.nju.cs.seg.pojo.Comment;
import cn.edu.nju.cs.seg.pojo.User;
import cn.edu.nju.cs.seg.service.AnswerService;
import cn.edu.nju.cs.seg.service.CommentService;
import cn.edu.nju.cs.seg.service.UserService;
import cn.jpush.api.push.model.PushPayload;
import org.junit.Test;

/**
 * Created by fwz on 2017/7/9.
 */
public class JPushUtilTests {
    @Test
    public void testSendPush() throws Exception {
        PushPayload payload = JPushUtil.buildPushObject_android_alias_msg("1");
        JPushUtil.sendPush(payload);
    }

    @Test
    public void testAddComment() throws Exception {
        Answer answer = AnswerService.findAnswerById(31);
        User user = UserService.findUserById(1);
        Comment comment = new Comment(user, "12344", answer);
        System.out.println(comment.getAnswer().getAnswerer().getId());
        CommentService.add(comment);
    }
}
