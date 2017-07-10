package cn.edu.nju.cs.seg.service;

import cn.edu.nju.cs.seg.dao.VerificationCodeDaoImpl;
import cn.edu.nju.cs.seg.pojo.VerificationCode;

/**
 * Created by fwz on 2017/5/14.
 */
public class VerificationCodeService {
    private static VerificationCodeDaoImpl dao = new VerificationCodeDaoImpl();
    public void add(VerificationCode code) {
        dao.add(code);
    }

    public VerificationCode findCodeByEmailOrPhone(String emailOrPhone) {
        return dao.getCodeByEmailOrPhone(emailOrPhone);
    }

    public void remove(String emailOrPhone) {
        dao.remove(emailOrPhone);
    }

    public void update(VerificationCode code) {
        dao.update(code);
    }
}
