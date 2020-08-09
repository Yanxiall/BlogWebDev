package com.HYX.webDev.service;

import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;

public interface BlogTagService {
    PageResult getTagPage(PageUtil pageUtil);
    Boolean AddTag(String TagName);
    Boolean DeleteTag(Integer TagId);
    Boolean DeleteTagBatch(Integer[] ids);
}
