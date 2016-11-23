package com.fymod.ftf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fymod.ftf.config.ResultBase;
import com.fymod.ftf.dao.ParamDAO;
import com.fymod.ftf.domain.SysParam;
import com.fymod.ftf.service.UserService;
import com.fymod.ftf.service.VersionService;

@Controller
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private VersionService versionService;
    @Autowired 
    private ParamDAO paramDAO;
    
    public final static String APK_PATH = "/usr/local/apk/LNTelFace.apk";

    @RequestMapping("/registe.json")
    public @ResponseBody ResultBase registe(@RequestBody JSONObject json) {
        String imei = json.getString("imei");
        String mobile = json.getString("mobile");
        String nickname = json.getString("nickname");
        String password = json.getString("password");
        Integer type = json.getInt("type");
        String uid = json.getString("uid");
        String tid = json.getString("tid");
        String token = json.getString("token");
        return userService.registe(imei, mobile, nickname, password, type, uid, tid, token);
    }

    @RequestMapping("/login.json")
    public @ResponseBody ResultBase login(@RequestBody JSONObject json) {
        String mobile = json.getString("mobile");
        String password = json.getString("password");
        return userService.login(mobile, password);
    }

    @RequestMapping("/contact.json")
    public @ResponseBody ResultBase contact(@RequestBody JSONObject json) {
        Long id = json.getLong("id");
        return userService.getContact(id);
    }

    @RequestMapping("/updateToken.json")
    public @ResponseBody ResultBase updateToken(@RequestBody JSONObject json) {
        Long id = json.getLong("id");
        String token = json.getString("token");
        return userService.updateToken(id, token);
    }

    @RequestMapping("/getUserInfo.json")
    public @ResponseBody ResultBase getUserInfo(@RequestBody JSONObject json) {
        Long id = json.getLong("id");
        return userService.getUserInfo(id);
    }

    @RequestMapping("/getUserInfoByMobile.json")
    public @ResponseBody ResultBase getUserInfoByMobile(@RequestBody JSONObject json) {
        String mobile = json.getString("mobile");
        return userService.getUserInfoByMobile(mobile);
    }

    @RequestMapping("/updatePwd.json")
    public @ResponseBody ResultBase updatePwd(@RequestBody JSONObject json) {
        String mobile = json.getString("mobile");
        String newPassword = json.getString("newPassword");
        return userService.updatePwd(mobile, newPassword);
    }

    @RequestMapping("/sendMessage.json")
    public @ResponseBody ResultBase sendMessage(@RequestBody JSONObject json) {
        String mobile = json.getString("mobile");
        return userService.sendMessage(mobile);
    }

    @RequestMapping("/getBoxNum.json")
    public @ResponseBody ResultBase getBoxNum(@RequestBody JSONObject json) {
        String deviceId = json.getString("deviceId");
        return userService.getBoxNum(deviceId);
    }

    @RequestMapping("/getVersionInfo.json")
    public @ResponseBody ResultBase getUpdateInfo(@RequestBody JSONObject json) {
        String type = json.getString("type");
        if(StringUtils.isEmpty(type)) {
            type = "1";
        }
        return versionService.getVersionInfo(type);
    }

    @RequestMapping("/getUpdateFile.json")
    public void getUpdateFile(int code, HttpServletResponse response) {
        try {
            SysParam param = paramDAO.searchParam(code);
            String url;
            if(param == null) {
                url = APK_PATH;
            }else {
                url = param.getParam();
            }
            handleFileDownload(response, url);
        } catch (Exception ex) {

        }
    }

    /**
     * @功能描述：处理文件下载
     * @developer：xiaona
     * @date：2016年9月27日 下午4:46:18
     * @param response
     * @param fileRealPath
     *            文件绝对路径
     * @throws Exception
     *             void
     */
    private static void handleFileDownload(HttpServletResponse response, String fileRealPath) throws Exception {
        try {
            File file = new File(fileRealPath);
            if (!file.exists() || !file.isFile()) {
                return;
            }
            String fileName = file.getName();
            FileInputStream in = new FileInputStream(file);// 获得资源
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setContentType("application/octet-stream");
            OutputStream os = response.getOutputStream();
            try {
                byte[] b = new byte[2048];
                while (in.read(b) > 0) {
                    os.write(b);
                }
            } finally {
                os.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
