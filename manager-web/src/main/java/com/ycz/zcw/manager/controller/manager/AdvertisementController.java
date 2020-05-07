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
 * @Description TODO(�����������)
 * @author Administrator
 * @Date 2020��5��6�� ����5:18:31
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/advertisement/")
public class AdvertisementController {

    @Autowired
    private AdvertisementService aService;

    /**
     * @Description����ת��������ҳ�棩 @return
     */
    @RequestMapping("index")
    public String index() {
        return "manager/servicemanager/advertisement/index";
    }

    /**
     * @Description (��֤����Ƿ��ظ�)
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("validateAdName")
    public Object validateAdName(String name) {
        AjaxResult result = new AjaxResult();
        Advertisement ad = aService.queryAdByName(name);// ��ѯ�Ƿ��ظ�
        if (ad == null) {// ����
            result.setSuccess(true);
        } else {// �ظ�
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    //����Ӧͷ��Contont-Type,�������
    @RequestMapping(value="upload")
    public Object upload(HttpSession session, @RequestParam(value = "file", required = true) MultipartFile file,
            String name) {
        AjaxResult result = new AjaxResult();
        try {
            //������Ƿ����
            Advertisement a = aService.queryAdByName(name);
            if(a==null) {//�����ڣ������
                ServletContext context = session.getServletContext();//��ȡ�����Ķ���
                String adPath = context.getRealPath("/ads/");//��ȡ���ͼƬ��ŵ�·��
                //ÿһ��ͼƬ�ľ���·��
                String fileName = UUID.randomUUID().toString().replace("-", "").substring(0,10) + "_file_" + file.getOriginalFilename();
                    //��ȡ�ļ��ϴ��������λ��
                    String netUrl = "ads/" + fileName;
                    Advertisement adver = new Advertisement();
                    adver.setName(name);
                    adver.setUrl(netUrl);
                    //��ȡ��ǰ���������
                    User user = (User) session.getAttribute(Constants.LOGIN_USER);
                    adver.setUserid(user.getId());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    adver.setCreatetime(sdf.format(new Date()));
                    //���浽���ݿ�
                    aService.addAdvertise(adver);
                    //�ļ��ϴ��ŵ���Ӽ�¼֮ǰ���������ʧ��ͼƬ�Ͳ����ϴ�
                    file.transferTo(new File(adPath + fileName));//�ļ��ϴ�
                    result.setSuccess(true);
            }else {
                result.setData("�ù���Ѵ��ڣ�");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("���ʧ�ܣ�");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (��ҳ��ѯ)
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
            // ��ѯ��Ҫ�ļ�¼
            List<Advertisement> ads = aService.queryAdsPaged(map);
            // ��ȡ�ܼ�¼����
            int totalSize = aService.getAdsTotal(map);
            // ��ȡ���ҳ����
            int maxPage = (totalSize % pageSize == 0) ? totalSize / pageSize : (totalSize / pageSize) + 1;
            // ʹ�÷�ҳ�����װ����
            Page<Advertisement> adPage = new Page<>();
            adPage.setDatas(ads);
            adPage.setMaxPage(maxPage);
            adPage.setPage(page);
            adPage.setTotalSize(totalSize);
            result.setData(adPage);// ��װ��result�з���ǰ̨
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (����)
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
     * @Description (����޸�)
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
            Advertisement ad = aService.queryAdById(id);//���Ҫ�޸ĵĹ���¼
            //�ж��Ƿ���ļ�
            //��������ļ�����ôֻ�޸�����
            if(file==null) {
                Advertisement adver = new Advertisement();
                adver.setId(id);
                adver.setName(name);
                aService.editAd(adver);
                result.setSuccess(true);
            }else {
                //������ļ���ѡ���ͼƬ��ԭͼƬ������ֻ�޸Ĺ������
                if(ad.getUrl().contains(file.getOriginalFilename())) {
                    Advertisement adver = new Advertisement();
                    adver.setId(id);
                    adver.setName(name);
                    aService.editAd(adver);
                    result.setSuccess(true);
                }else {//�޸Ĺ���������Ҹ���ͼƬ��ɾ��ԭͼƬ
                    ServletContext context = session.getServletContext();//��ȡ�����Ķ���
                    String adPath = context.getRealPath("/ads/");//��ȡ���ͼƬ��ŵ�·��
                    //ÿһ��ͼƬ�ľ���·��
                    String fileName = UUID.randomUUID().toString().replace("-", "").substring(0,10) + "_file_" + file.getOriginalFilename();
                        //��ȡ�ļ��ϴ��������λ��
                        String netUrl = "ads/" + fileName;
                        file.transferTo(new File(adPath + fileName));//�ļ��ϴ�
                        Advertisement adver = new Advertisement();
                        adver.setId(id);
                        adver.setName(name);
                        adver.setUrl(netUrl); 
                        aService.editAd(adver);
                        //��ȡӦ�õľ���·��
                        String path = session.getServletContext().getRealPath("").replace('\\','/');
                        String picLocation = path.substring(0,path.indexOf("target"));//��ȡ�ַ���
                        String absPath = picLocation  + "webapp/" + ad.getUrl();//��ȡͼƬ�ľ���·��
                        File f = new File(absPath); 
                        if(f.exists()) {
                            f.delete(); 
                        }
                        result.setSuccess(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("�ù���Ѵ��ڣ�");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (�������ɾ��)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Object delete(Integer id,HttpSession session) {
        AjaxResult result = new AjaxResult();
        try {
            //ɾ�����ͼƬ
            Advertisement ad = aService.queryAdById(id);//���Ҫ�޸ĵĹ���¼
            String path = session.getServletContext().getRealPath("").replace('\\','/');
            String picLocation = path.substring(0,path.indexOf("target"));//��ȡ�ַ���
            String absPath = picLocation  + "webapp/" + ad.getUrl();//��ȡͼƬ�ľ���·��
            File f = new File(absPath); 
            if(f.exists()) {
                f.delete(); 
            }
            aService.delete(id);//ɾ�����ݿ��еļ�¼
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (����ɾ��)
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteAds")
    public Object deleteAds(String ids,HttpSession session) {
        AjaxResult result = new AjaxResult();
        try {
            String path = session.getServletContext().getRealPath("").replace('\\','/');
            String picLocation = path.substring(0,path.indexOf("target"));//��ȡ�ַ���
            String adverIds = ids.substring(0,ids.length()-1);//ȥ��ĩβ�Ķ���
            String str[] = adverIds.split(",");//�ָ��ַ���
            for(int i=0;i<str.length;i++) {//ɾ��ͼƬ
                Advertisement ad = aService.queryAdById(Integer.parseInt(str[i]));//���Ҫɾ���Ĺ���¼
                File f = new File(picLocation  + "webapp/" + ad.getUrl()); 
                if(f.exists()) {
                    f.delete(); 
                }
            }
            aService.deleteAds(adverIds);//ɾ�����ݿ��м�¼
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
