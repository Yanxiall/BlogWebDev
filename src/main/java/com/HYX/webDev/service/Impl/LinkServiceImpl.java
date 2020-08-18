package com.HYX.webDev.service.Impl;

import com.HYX.webDev.dao.LinkMapper;
import com.HYX.webDev.entity.BlogCategory;
import com.HYX.webDev.entity.Link;
import com.HYX.webDev.service.LinkService;
import com.HYX.webDev.util.PageResult;
import com.HYX.webDev.util.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class LinkServiceImpl implements LinkService {
    @Resource
    private LinkMapper linkMapper;
    @Override
    public PageResult getLinkPage(PageUtil pageUtil){
        //the data of the current page
        List<Link> links = linkMapper.findLink(pageUtil);
        //the number of all records
        int total = linkMapper.getTotalLink(pageUtil);
        PageResult pageResult = new PageResult(links, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
    @Override
    public Boolean AddLink(Byte Linktype,String websiteName,String url,String websiteDescription,Integer rank){
        Link link= new Link();
        link.setLinkType(Linktype.byteValue());
        link.setLinkName(websiteName);
        link.setLinkUrl(url);
        link.setLinkDescription(websiteDescription);
        link.setLinkRank(rank);
        if(linkMapper.insertSelective(link) > 0){
            return true;
        }
        return false;
    }
    @Override
    public Boolean ModifyLink(Long linkId,Byte Linktype,String websiteName,String url,String websiteDescription,Integer rank){
        Link link =  linkMapper.selectByPrimaryKey(linkId);
        link.setLinkType(Linktype);
        link.setLinkName(websiteName);
        link.setLinkUrl(url);
        link.setLinkDescription(websiteDescription);
        link.setLinkRank(rank);
        if(linkMapper.updateByPrimaryKeySelective(link) > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean DeleteLinkBatch(Long[] ids){
        if(linkMapper.deleteBatch(ids) > 0){
            return true;
        }
        return false;
    }
    @Override
   public Link LinkInfo(Long linkId){
        Link link =  linkMapper.selectByPrimaryKey(linkId);
        return link;
    }
    @Override
    public List<Link> getAllLinks(){
        return  linkMapper.findAllLinks();
    }

}
