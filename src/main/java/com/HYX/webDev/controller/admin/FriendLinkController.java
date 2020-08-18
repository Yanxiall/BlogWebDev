package com.HYX.webDev.controller.admin;

import com.HYX.webDev.service.LinkService;
import com.HYX.webDev.util.Constants;
import com.HYX.webDev.util.PageUtil;
import com.HYX.webDev.util.Result;
import com.HYX.webDev.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class FriendLinkController {
    @Resource
    private LinkService linkService;

    @GetMapping({"/friendlink"})
    public String friendlink() {
        return "admin/friendlink";
    }

    @RequestMapping(value ="/friendlink/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "wrong parameters!");
        }
        //query the page which is searched
        PageUtil pageUtil = new PageUtil(params);
        //return the frontend data
        return ResultGenerator.genSuccessResult(linkService.getLinkPage(pageUtil));
    }

    @RequestMapping(value ="/friendlink/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestParam("LinkType") Byte linkType,
                      @RequestParam("WebsiteName") String websiteName,
                      @RequestParam("URL") String linkUrl,
                      @RequestParam("Rank") Integer linkRank,
                      @RequestParam("WebsiteDescription") String websiteDescription){
        if(StringUtils.isEmpty(linkType))
        {
            return ResultGenerator.genFailResult("Please input link type!");
        }
        if( StringUtils.isEmpty(websiteName)){
            return ResultGenerator.genFailResult("Please input website name!");
        }
        if( StringUtils.isEmpty(linkUrl)){
            return ResultGenerator.genFailResult("Please input link url!");
        }
        if( StringUtils.isEmpty(linkRank)){
            return ResultGenerator.genFailResult("Please input link rank!");
        }
        if( StringUtils.isEmpty(websiteDescription)){
            return ResultGenerator.genFailResult("Please input website description!");
        }
        if(linkService.AddLink(linkType,websiteName,linkUrl,websiteDescription,linkRank)){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult("Duplicate Link!");
        }

    }

    @RequestMapping(value ="/friendlink/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modify(@RequestParam("LinkId") Long linkId,
                         @RequestParam("LinkType") Byte linkType,
                         @RequestParam("WebsiteName") String websiteName,
                         @RequestParam("URL") String linkUrl,
                         @RequestParam("Rank") Integer linkRank,
                         @RequestParam("WebsiteDescription") String websiteDescription
    ){
        if (linkId == null || linkId < 1) {
            return ResultGenerator.genFailResult("invalid parameter!");
        }
        if(StringUtils.isEmpty(linkType))
        {
            return ResultGenerator.genFailResult("Please input link type!");
        }
        if( StringUtils.isEmpty(websiteName)){
            return ResultGenerator.genFailResult("Please input website name!");
        }
        if( StringUtils.isEmpty(linkUrl)){
            return ResultGenerator.genFailResult("Please input link url!");
        }
        if( StringUtils.isEmpty(linkRank)){
            return ResultGenerator.genFailResult("Please input link rank!");
        }
        if( StringUtils.isEmpty(websiteDescription)){
            return ResultGenerator.genFailResult("Please input website description!");
        }
        if(linkService.ModifyLink(linkId,linkType,websiteName,linkUrl,websiteDescription,linkRank)){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult("Duplicate Link!");
        }
    }

    @RequestMapping(value ="/link/info", method = RequestMethod.GET)
    @ResponseBody
    public Result info(@RequestParam Long LinkId){

        return ResultGenerator.genSuccessResult(linkService.LinkInfo(LinkId));
    }

    @RequestMapping(value ="/link/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids){
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("invalid parameter!");
        }
        if(linkService.DeleteLinkBatch(ids)){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult("Delete Fail!");
        }
    }

}
