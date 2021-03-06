package com.luguosong._04_structural._07_proxy_pattern.static_proxy;

import com.luguosong._04_structural._07_proxy_pattern.AccessValidator;
import com.luguosong._04_structural._07_proxy_pattern.Logger;
import com.luguosong._04_structural._07_proxy_pattern.RealSearcher;
import com.luguosong._04_structural._07_proxy_pattern.Searcher;

/**
 * 代理查询类，充当代理主题角色
 *
 * @author luguosong
 * @date 2022/5/27 17:16
 */
public class ProxySearcher implements Searcher {

    private RealSearcher searcher = new RealSearcher();
    private AccessValidator validator;
    private Logger logger;

    @Override
    public String doSearch(String userId, String keyword) {
        if (this.validate(userId)) {
            String result = searcher.doSearch(userId, keyword);
            this.log(userId);
            return result;
        } else {
            return null;
        }
    }

    public boolean validate(String userId) {
        validator = new AccessValidator();
        return validator.validate(userId);
    }

    public void log(String userId) {
        logger = new Logger();
        logger.log(userId);
    }
}
