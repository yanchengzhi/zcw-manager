package com.ycz.zcw.manager.controller.manager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ycz.zcw.manager.pojo.Advertisement;
import com.ycz.zcw.manager.pojo.AjaxResult;
import com.ycz.zcw.manager.pojo.Page;
import com.ycz.zcw.manager.pojo.TType;
import com.ycz.zcw.manager.pojo.User;
import com.ycz.zcw.manager.pojo.constants.Constants;
import com.ycz.zcw.manager.service.AdvertisementService;

/**
 * @ClassName AdvertisementController
 * @Description TODO(广告管理控制器)
 * @author Administrator
 * @Date 2020年5月6日 下午5:18:31
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/advertisement/")
public class AdvertisementController {

    @Autowired
    private AdvertisementService aService;

    /**
     * @Description（跳转到广告管理页面） @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/servicemanager/advertisement/index";
    }

    /**
     * @Description (验证广告是否重复)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateAdName")
    public Object validateAdName(String name) {
        AjaxResult result = new AjaxResult();
        Advertisement ad = aService.queryAdByName(name);// 查询是否重复
        if (ad == null) {// 可用
            result.setSuccess(true);
        } else {// 重复
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    //给响应头加Contont-Type,解决乱码
    @RequestMapping(value="upload")
    public Object upload(HttpSession session, @RequestParam(value = "file", required = true) MultipartFile file,
            String name) {
        AjaxResult result = new AjaxResult();
        try {
            //看广告是否存在
            Advertisement a = aService.queryAdByName(name);
            if(a==null) {//不存在，可添加
                ServletContext context = session.getServletContext();//获取上下文对象
                String adPath = context.getRealPath("/ads/");//获取广告图片存放的路径
                //每一张图片的绝对路径
                String fileName = UUID.randomUUID().toString().replace("-", "").substring(0,10) + "_file_" + file.getOriginalFilename();
                    //获取文件上传后的网络位置
                    String netUrl = "ads/" + fileName;
                    Advertisement adver = new Advertisement();
                    adver.setName(name);
                    adver.setUrl(netUrl);
                    //获取当前作用域对象
                    User user = (User) session.getAttribute(Constants.LOGIN_USER);
                    adver.setUserid(user.getId());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    adver.setCreatetime(sdf.format(new Date()));
                    //保存到数据库
                    aService.addAdvertise(adver);
                    //文件上传放到添加记录之前，这样添加失败图片就不会上传
                    file.transferTo(new File(adPath + fileName));//文件上传
                    result.setSuccess(true);
            }else {
                result.setData("该广告已存在！");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("添加失败！");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (分页查询)
     * @param page
     * @param pageSize
     * @param queryText
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Object list(@RequestParam(value = "page", required = true) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "queryText", required = false) String queryText) {
        AjaxResult result = new AjaxResult();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", (page - 1) * pageSize);
            map.put("pageSize", pageSize);
            map.put("queryText", queryText);
            // 查询需要的记录
            List<Advertisement> ads = aService.queryAdsPaged(map);
            // 获取总记录条数
            int totalSize = aService.getAdsTotal(map);
            // 获取最大页码数
            int maxPage = (totalSize % pageSize == 0) ? totalSize / pageSize : (totalSize / pageSize) + 1;
            // 使用分页对象封装数据
            Page<Advertisement> adPage = new Page<>();
            adPage.setDatas(ads);
            adPage.setMaxPage(maxPage);
            adPage.setPage(page);
            adPage.setTotalSize(totalSize);
            result.setData(adPage);// 封装到result中返给前台
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (回显)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("getAd")
    public Object getAd(Integer id) {
        AjaxResult result = new AjaxResult();
        Advertisement ad = aService.queryAdById(id);
        result.setData(ad);
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 
     * @Description (广告修改)
     * @param session
     * @param file
     * @param name
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("editAd")
    public Object editAd(HttpSession session,@RequestParam(value = "file", required = false) MultipartFile file,
            String name,Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            Advertisement ad = aService.queryAdById(id);//查出要修改的广告记录
            //判断是否带文件
            //如果不带文件，那么只修改名称
            if(file==null) {
                Advertisement adver = new Advertisement();
                adver.setId(id);
                adver.setName(name);
                aService.editAd(adver);
                result.setSuccess(true);
            }else {
                //如果带文件，选择的图片是原图片，还是只修改广告名称
                if(ad.getUrl().contains(file.getOriginalFilename())) {
                    Advertisement adver = new Advertisement();
                    adver.setId(id);
                    adver.setName(name);
                    aService.editAd(adver);
                    result.setSuccess(true);
                }else {//修改广告名，并且更换图片，删除原图片
                    ServletContext context = session.getServletContext();//获取上下文对象
                    String adPath = context.getRealPath("/ads/");//获取广告图片存放的路径
                    //每一张图片的绝对路径
                    String fileName = UUID.randomUUID().toString().replace("-", "").substring(0,10) + "_file_" + file.getOriginalFilename();
                        //获取文件上传后的网络位置
                        String netUrl = "ads/" + fileName;
                        file.transferTo(new File(adPath + fileName));//文件上传
                        Advertisement adver = new Advertisement();
                        adver.setId(id);
                        adver.setName(name);
                        adver.setUrl(netUrl); 
                        aService.editAd(adver);
                        //获取应用的绝对路径
                        String path = session.getServletContext().getRealPath("").replace('\\','/');
                        String picLocation = path.substring(0,path.indexOf("target"));//截取字符串
                        String absPath = picLocation  + "webapp/" + ad.getUrl();//获取图片的绝对路径
                        File f = new File(absPath); 
                        if(f.exists()) {
                            f.delete(); 
                        }
                        result.setSuccess(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("该广告已存在！");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (单条广告删除)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Object delete(Integer id,HttpSession session) {
        AjaxResult result = new AjaxResult();
        try {
            //删除广告图片
            Advertisement ad = aService.queryAdById(id);//查出要修改的广告记录
            String path = session.getServletContext().getRealPath("").replace('\\','/');
            String picLocation = path.substring(0,path.indexOf("target"));//截取字符串
            String absPath = picLocation  + "webapp/" + ad.getUrl();//获取图片的绝对路径
            File f = new File(absPath); 
            if(f.exists()) {
                f.delete(); 
            }
            aService.delete(id);//删除数据库中的记录
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (批量删除)
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteAds")
    public Object deleteAds(String ids,HttpSession session) {
        AjaxResult result = new AjaxResult();
        try {
            String path = session.getServletContext().getRealPath("").replace('\\','/');
            String picLocation = path.substring(0,path.indexOf("target"));//截取字符串
            String adverIds = ids.substring(0,ids.length()-1);//去掉末尾的逗号
            String str[] = adverIds.split(",");//分割字符串
            for(int i=0;i<str.length;i++) {//删除图片
                Advertisement ad = aService.queryAdById(Integer.parseInt(str[i]));//查出要删除的广告记录
                File f = new File(picLocation  + "webapp/" + ad.getUrl()); 
                if(f.exists()) {
                    f.delete(); 
                }
            }
            aService.deleteAds(adverIds);//删除数据库中记录
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
