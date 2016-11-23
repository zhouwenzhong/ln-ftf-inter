package com.fymod.ftf.service.impl;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fymod.ftf.config.ResultBase;
import com.fymod.ftf.dao.VersionDAO;
import com.fymod.ftf.domain.Version;
import com.fymod.ftf.service.VersionService;

@Service("versionService") @Transactional
public class VersionServiceImpl implements VersionService {
    @Autowired private VersionDAO versionDAO;
    @Override
    public ResultBase getVersionInfo(String type) {
        ResultBase ret = new ResultBase();
        Version version = versionDAO.search(type);
        if(version == null) {
            JSONObject json = new JSONObject();
            json.put("code", "0");
            json.put("name", "");
            json.put("feature", "");
            json.put("downLoadUrl", "");
            ret.setObj(json);
            ret.setResult(ResultBase.RESULT_SUCC);
        } else {
            JSONObject json = new JSONObject();
            json.put("code", version.getCode());
            json.put("name", version.getName());
            json.put("feature", version.getFeature());
            json.put("downLoadUrl", version.getTargetUrl());
            ret.setObj(json);
            ret.setResult(ResultBase.RESULT_SUCC);
        }
        return ret;
    }
}
