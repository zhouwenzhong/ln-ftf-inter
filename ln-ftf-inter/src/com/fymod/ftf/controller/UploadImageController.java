package com.fymod.ftf.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fymod.ftf.config.Constant;
import com.fymod.ftf.config.ErrorCode;
import com.fymod.ftf.config.ResultBase;
import com.fymod.ftf.dao.ClientUserDAO;
import com.fymod.ftf.domain.ClientUser;
import com.fymod.ftf.form.UploadImgForm;
import com.fymod.ftf.util.TextUtil;

@Controller
@Transactional
@RequestMapping("/upload")
public class UploadImageController {
    @Autowired
    private ClientUserDAO clientUserDAO;

    @RequestMapping(value = "/image.json", method = { RequestMethod.POST })
    public @ResponseBody ResultBase avatar(@ModelAttribute UploadImgForm form) {
        ResultBase result = new ResultBase();
        result.setResult(ResultBase.RESULT_SUCC);
        String fileUrl = "";
        try {
            MultipartFile file = form.getFile();
            String filePath = getFileName(file.getOriginalFilename());
            filePath = form.getType() + "/" + filePath;
            String saveFilePath = Constant.UPLOAD_IMAGE_PATH + filePath;
            File saveFile = new File(saveFilePath);
            File parentFile = new File(saveFile.getParent());
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            saveFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(saveFile);
            InputStream is = file.getInputStream();
            try {
                byte[] b = new byte[2048];
                while (is.read(b) > 0) {
                    fos.write(b);
                    fos.flush();
                }
                fileUrl = Constant.DOWNLOAD_IMAGE_PATH + filePath;
            } finally {
                is.close();
                fos.close();
            }
            
            ClientUser user = clientUserDAO.findById(Long.parseLong(form.getId()));
            user.setHeadUrl(fileUrl);
            clientUserDAO.saveOrUpdate(user);
        } catch (Exception e) {
            result = result.error(ErrorCode.SYS_ERROR_CODE_405);
        }

        result.setObj(fileUrl);
        return result;
    }

    protected String getFileName(String originalFilename) {
        String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = TextUtil.MD5(Long.toString(System.currentTimeMillis()) + TextUtil.getNonceStr(20));
        return fileName + prefix;
    }

}
