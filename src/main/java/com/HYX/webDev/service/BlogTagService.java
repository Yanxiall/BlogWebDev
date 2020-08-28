package com.HYX.webDev.service;

import com.HYX.webDev.entity.TagShow;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;

import java.util.List;

public interface BlogTagService {
    PageResult getTagPage(PageUtil pageUtil);
    Boolean AddTag(String TagName);
    Boolean DeleteTag(Integer TagId);
    Boolean DeleteTagBatch(Integer[] ids);
    public List<TagShow> BlogTagShow();
}
