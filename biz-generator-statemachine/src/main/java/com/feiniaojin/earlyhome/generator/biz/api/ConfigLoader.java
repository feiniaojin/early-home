package com.feiniaojin.earlyhome.generator.biz.api;

import com.feiniaojin.earlyhome.generator.biz.bean.ConfigBean;

public interface ConfigLoader {

    ConfigBean loadConfigFile() throws Exception;
}
