package com.fymod.ftf.controller;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fymod.ftf.config.Constant;
import com.fymod.ftf.config.ErrorCode;
import com.fymod.ftf.config.ResultBase;
import com.fymod.ftf.dao.SmsDao;
import com.fymod.ftf.domain.ClientUser;
import com.fymod.ftf.domain.SmsCode;
import com.fymod.ftf.service.UserService;
import com.fymod.ftf.util.TemplateSMS;
import com.fymod.ftf.util.TextUtil;

@Controller
@RequestMapping(value = "/register")
@Transactional
public class RegisterController {
    private boolean isdebug = false;
    @Autowired
    private UserService userService;

    @Autowired
    private SmsDao smsDao;

    @RequestMapping(value = "/getcheckcode.json", method = { RequestMethod.POST }, produces = "application/json")
    public @ResponseBody ResultBase getCheckCode(@RequestBody JSONObject json) {
        String mobile = json.getString("mobile");
        ClientUser user = userService.getUserByMobile(mobile);
        if (user != null) {
            return new ResultBase().error(ErrorCode.SYS_ERROR_CODE_1006);
        }

        Long count = smsDao.count("select count(*) from SmsCode where mobile=?0 and addTime>?1 and type=?2 and state=?3",
                new Object[] { mobile, new Date(System.currentTimeMillis() - Constant.SMS_INTERVAL),
                        Constant.SMS_CODE_TYPE_REGISTER, "" });
        // 发送频繁
        if (count > 0) {
            return new ResultBase().error(ErrorCode.SYS_ERROR_CODE_1001);
        }
        count = smsDao.count("select count(*) from SmsCode where mobile=?0 and addTime>?1 and type=?2 and state=?3",
                new Object[] { mobile, new Date(System.currentTimeMillis() - Constant.SMS_CODE_VALID),
                        Constant.SMS_CODE_TYPE_REGISTER, "1" });
        // 上次 发送的已经过期
        ResultBase ret = new ResultBase();
        if (count == 0) {
            String code = TextUtil.getNonceNum(6);
            // smsMsgImpl.sendSMS(new
            // SmsParameter().setSmsType("CCP").setMobile(mobile)
            // .setTemplateId(Aptitude.getInstance().smsCcp.templateId)
            // .setParameter(new String[] { code,
            // String.valueOf(Constant.SMS_CODE_VALID / 1000 / 60) }));
            if (!isdebug) {
                // 135757  130866
                ret = TemplateSMS.send(mobile, "135757",
                        new String[] { code, String.valueOf(Constant.SMS_CODE_VALID / 1000 / 60) });
            } else {
                ret.setResult("1");
            }
            SmsCode smsCode = new SmsCode();
            smsCode.setState("1");
            smsCode.setCode(code);
            smsCode.setAddTime(new Date());
            smsCode.setMobile(mobile);
            smsCode.setType(Constant.SMS_CODE_TYPE_REGISTER);
            smsDao.save(smsCode);
        } else {
            ret.setResult(ResultBase.RESULT_SUCC);
        }

        return ret;
    }

    @RequestMapping(value = "/verifycheckcode.json", method = { RequestMethod.POST }, produces = "application/json")
    public @ResponseBody ResultBase verifyCheckCode(@RequestBody JSONObject json) {
        String mobile = json.getString("mobile");
        String checkcode = json.getString("checkcode");
        SmsCode smsCode = smsDao.findOne("from SmsCode where mobile=?0 and addTime>?1 and type=?2 and state=?3 and code=?4",
                new Object[] { mobile, new Date(System.currentTimeMillis() - Constant.SMS_CODE_VALID),
                        Constant.SMS_CODE_TYPE_REGISTER, "1", checkcode });
        if (smsCode == null) {
            return new ResultBase().error(ErrorCode.SYS_ERROR_CODE_1002);
        } else {
            smsCode.setState("0");
            smsDao.update(smsCode);
        }

        ResultBase ret = new ResultBase();
        ret.setResult(ResultBase.RESULT_SUCC);
        return ret;
    }

    @RequestMapping(value = "/forgetcheckcode.json", method = { RequestMethod.POST }, produces = "application/json")
    public @ResponseBody ResultBase forgetCheckCode(@RequestBody JSONObject json) {

        ResultBase ret = new ResultBase();
        String mobile = json.getString("mobile");
        ClientUser user = userService.getUserByMobile(mobile);
        if (user == null) {
            return new ResultBase().error(ErrorCode.SYS_ERROR_CODE_1007);
        }

        Long count = smsDao.count("select count(*) from SmsCode where mobile=?0 and addTime>?1 and type=?2 and state=?3",
                new Object[] { mobile, new Date(System.currentTimeMillis() - Constant.SMS_INTERVAL),
                        Constant.SMS_CODE_TYPE_FORGET, "" });
        // 发送频繁
        if (count > 0) {
            return new ResultBase().error(ErrorCode.SYS_ERROR_CODE_1001);
        }
        SmsCode sms = smsDao.findOne("from SmsCode where mobile=?0 and addTime>?1 and type=?2 and state=?3", new Object[] {
                mobile, new Date(System.currentTimeMillis() - Constant.SMS_CODE_VALID), Constant.SMS_CODE_TYPE_FORGET, "1" });
        // 上次 发送的已经过期
        if (count == 0) {
            String code = TextUtil.getNonceNum(6);
            if (!isdebug) {
                // 135757  130866
                ret = TemplateSMS.send(mobile, "135757",
                        new String[] { code, String.valueOf(Constant.SMS_CODE_VALID / 1000 / 60) });
            } else {
                ret.setResult("1");
            }
            SmsCode smsCode = new SmsCode();
            smsCode.setState("1");
            smsCode.setCode(code);
            smsCode.setAddTime(new Date());
            smsCode.setMobile(mobile);
            smsCode.setType(Constant.SMS_CODE_TYPE_FORGET);
            smsDao.save(smsCode);
            ret.setObj(code);
        } else {
            ret.setObj(sms.getCode());
            ret.setResult(ResultBase.RESULT_SUCC);
        }

        return ret;
    }

    @RequestMapping(value = "/verifyforgetcheckcode.json", method = { RequestMethod.POST }, produces = "application/json")
    public @ResponseBody ResultBase verifyForgetCheckCode(@RequestBody JSONObject json) {
        String mobile = json.getString("mobile");
        String checkcode = json.getString("checkcode");
        SmsCode smsCode = smsDao.findOne("from SmsCode where mobile=?0 and addTime>?1 and type=?2 and state=?3 and code=?4",
                new Object[] { mobile, new Date(System.currentTimeMillis() - Constant.SMS_CODE_VALID),
                        Constant.SMS_CODE_TYPE_FORGET, "1", checkcode });
        if (smsCode == null) {
            return new ResultBase().error(ErrorCode.SYS_ERROR_CODE_1002);
        } else {
            smsCode.setState("0");
            smsDao.update(smsCode);
        }

        ResultBase ret = new ResultBase();
        ret.setResult(ResultBase.RESULT_SUCC);
        return ret;
    }
}
