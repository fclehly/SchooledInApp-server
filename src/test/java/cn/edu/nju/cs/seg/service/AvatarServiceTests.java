package cn.edu.nju.cs.seg.service;

import cn.edu.nju.cs.seg.pojo.Avatar;
import org.junit.Test;

/**
 * Created by fwz on 2017/7/8.
 */
public class AvatarServiceTests {

    @Test
    public void testAddAvatar() throws Exception {
        Avatar avatar = new Avatar("test2", ".jpg");
        Avatar a = AvatarService.add(avatar);
        System.out.println(avatar.getId());
    }
}
