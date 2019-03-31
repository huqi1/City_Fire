package com.hq.modules.operate.controller;

import com.hq.common.utils.DateUtils;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.PoiUtils;
import com.hq.common.utils.R;
import com.hq.modules.operate.entity.LocationEntity;
import com.hq.modules.operate.service.LocationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;


/**
 *
 *
 * @author hq
 * @date 2018-12-17 09:25:46
 */
@Controller
@RequestMapping("location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @Value("${spring.file.basefileroot}")
    private  String  FILEROOTBASE;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("operate:location:list")
    public R list(@RequestParam Map<String, Object> params){
        params.put("sidx","gmt_create");
        params.put("order","DESC");
        PageUtils page = locationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 导出
     */
    @RequestMapping("/export")
    @RequiresPermissions("operate:location:list")
    public void export(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws IOException {
        params.put("sidx","gmt_create");
        params.put("order","DESC");
        PageUtils page = locationService.queryPage(params);
        List<LocationEntity> locations = (List<LocationEntity>) page.getList();
        String sheetName = "";
        String fileName = "";
        if(params.get("page").equals("1") && params.get("limit").equals("2000")){
            sheetName = "所有页位置数据"+DateUtils.getNowTimetohm();
        }else {
            sheetName = "当前页位置数据"+DateUtils.getNowTimetohm();
        }
         String sheetTitle  = "位置数据";
        fileName = sheetName;
        List<String> columnNames = new LinkedList<>();
        columnNames.add("位置id");
        columnNames.add("所属省份");
        columnNames.add("所属市");
        columnNames.add("市级编码");
        columnNames.add("所属区");
        columnNames.add("区级编码");
        columnNames.add("街道");
        columnNames.add("街道编码");
        columnNames.add("位置名称");
        columnNames.add("纬度");
        columnNames.add("经度");
        columnNames.add("状态");
        columnNames.add("备注");
        columnNames.add("创建时间");

        String excelName = "文件名";
        //写入标题--第二种方式
        try {
            PoiUtils.writeExcelTitle(FILEROOTBASE, fileName, sheetName, columnNames, sheetTitle, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> data = new LinkedList<>();
        int size = locations.size();
        for (int i = 0; i < size; i++) {
            List<Object> dataA = new LinkedList<>();
            dataA.add(locations.get(i).getLocationId());
            dataA.add(locations.get(i).getProvince());
            dataA.add(locations.get(i).getCity());
            dataA.add(locations.get(i).getAdcode());
            dataA.add(locations.get(i).getDistrict());
            dataA.add(locations.get(i).getAdcode());
            dataA.add(locations.get(i).getStreet());
            dataA.add(locations.get(i).getStreetNumber());
            dataA.add(locations.get(i).getLocationName());
            dataA.add(locations.get(i).getLat());
            dataA.add(locations.get(i).getLng());
            if(locations.get(i).getStatus() == 1) {
                dataA.add("禁用");
            }else{
                dataA.add("正常");
            }
            dataA.add(locations.get(i).getRemark());
            dataA.add(locations.get(i).getGmtCreate());
            data.add(dataA);
        }
        try {
            //写入数据--第二种方式
         File file = PoiUtils.writeExcelData(FILEROOTBASE, fileName, sheetName, data);
            } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fileName = URLDecoder.decode(fileName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file=new File(FILEROOTBASE+"/"+fileName+".xls");
        if (file.exists()) {
            String filepath = FILEROOTBASE+"/"+fileName+".xls";
            // 为了使下载框中显示中文文件名称不出乱码！必须对文件名进行编码
            String filename = getFilename(request,fileName);
            String contentDisposition = "attachment;filename=" + filename;
            // 一个流
            FileInputStream input = new FileInputStream(filepath);
            //设置头
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", contentDisposition);
            response.setContentType("application/octet-stream");
            response.setContentLength((int) file.length());

            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                byte[] buffer = new byte[128];
                int count = 0;
                while ((count = fis.read(buffer)) > 0) {
                    response.getOutputStream().write(buffer, 0, count);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.getOutputStream().flush();
                response.getOutputStream().close();
                fis.close();
                file.delete();
            }
        }else {
            response.setStatus(404);
        }
    }

    // 根据不同的浏览器进行编码设置，返回编码后的文件名
    public String getFilename(HttpServletRequest request, String filename) throws UnsupportedEncodingException {
        String agent = request.getHeader("User-Agent"); //获取浏览器
        if (agent.contains("Firefox")) {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?"
                    + base64Encoder.encode(filename.getBytes("utf-8"))
                    + "?=";
        } else if(agent.contains("MSIE")) {
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{locationId}")
    @ResponseBody
    @RequiresPermissions("operate:location:info")
    public R info(@PathVariable("locationId") String locationId){
        LocationEntity cfLocation = locationService.selectById(locationId);

        return R.ok().put("cfLocation", cfLocation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    @RequiresPermissions("operate:location:save")
    public R save(@RequestBody LocationEntity cfLocation){
        int lat = cfLocation.getLat().intValue();
        int lng = cfLocation.getLng().intValue();
        cfLocation.setLocationId(cfLocation.getCitycode()+cfLocation.getAdcode()+lat+""+lng+DateUtils.getHHmmssTime());
        cfLocation.setStatus(0);
        cfLocation.setGmtCreate(new Date());
        locationService.insertAllColumn(cfLocation);
        return R.ok();
    }

    /**
     * 修改备注
     */
    @RequestMapping("/updateRemark")
    @ResponseBody
    @RequiresPermissions("operate:location:updateRemark")
    public R updateRemark(@RequestParam Map<String,Object> params){
        String locationid = params.get("locationid").toString();
        String remark = " ";
        if(params.get("remark") == null || params.get("remark").equals("")){
        }else remark = params.get("remark").toString();
        locationService.updateRemarkByid(locationid,remark);
        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    @RequiresPermissions("operate:location:updateStatus")
    public R updateStatus(@RequestParam Map<String,Object> params){
        String locationid = params.get("locationid").toString();
        String status = params.get("status").toString();
        locationService.updateStatusByid(locationid,status);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions("operate:location:delete")
    public R delete(@RequestBody String[] locationIds){
        locationService.deleteBatchIds(Arrays.asList(locationIds));
        return R.ok();
    }

    /**
     * 根据市(citycode)查询所有区
     * */
    @RequestMapping("/districtList")
    @ResponseBody
    @RequiresPermissions("operate:location:list")
    public R districtList(@RequestParam Map<String, Object> params){
        //区列表不参与分页
        String citycode = params.get("citycode").toString();
        List<String> list = locationService.selectDistrictBycityCode(citycode);
        return R.ok().put("page", list);
    }
    @RequestMapping("/locationList")
    @ResponseBody
    @RequiresPermissions("operate:location:list")
    public R locationList(@RequestParam Map<String, Object> params){
        String district = params.get("district").toString();
        List<LocationEntity> list = locationService.selectBydistrict(district);
        return R.ok().put("page", list);
    }

}
