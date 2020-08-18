package com.HYX.webDev.service;

import com.HYX.webDev.entity.Link;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;

import java.util.List;

public interface LinkService {
    PageResult getLinkPage(PageUtil pageUtil);
    Boolean AddLink(Byte Linktype,String websiteName,String url,String websiteDescription,Integer rank);
    Boolean ModifyLink(Long linkId,Byte Linktype,String websiteName,String url,String websiteDescription,Integer rank);
    Boolean DeleteLinkBatch(Long[] ids);
    Link LinkInfo(Long linkId);
    List<Link> getAllLinks();
}
