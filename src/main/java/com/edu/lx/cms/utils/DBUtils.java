package com.edu.lx.cms.utils;

import com.edu.lx.cms.domain.po.User;

public interface DBUtils {
    User getUser(User user);

    void register(User user);
}
