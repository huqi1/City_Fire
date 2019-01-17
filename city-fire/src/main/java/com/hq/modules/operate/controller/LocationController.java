package com.hq.modules.operate.controller;

import com.hq.common.utils.DateUtils;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.R;
import com.hq.modules.operate.entity.LocationEntity;
import com.hq.modules.operate.service.LocationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
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
     * 列表
     */
    @RequestMapping("/export")
    @ResponseBody
    @RequiresPermissions("operate:location:list")
    public void list(@RequestParam Map<String, Object> params, HttpServletResponse response){
        params.put("sidx","gmt_create");
        params.put("order","DESC");
        PageUtils page = locationService.queryPage(params);
        List<LocationEntity> locations = (List<LocationEntity>) page.getList();
        String sheetName = "";
        if(params.get("page") != null){
           sheetName = "当前页位置数据"+DateUtils.getHHmmssTime();
        }else {
           sheetName = "所有页位置数据"+DateUtils.getHHmmssTime();
        }
         String sheetTitle  = "位置数据";
        List<String> columnNames = new LinkedList<>();
        columnNames.add("日期-String");
        columnNames.add("日期-Date");
        columnNames.add("时间戳-Long");
        columnNames.add("客户编码");
        columnNames.add("整数");
        columnNames.add("带小数的正数");
        
        response.setContentType("octets/stream");
        String excelName = "文件名";
        try {
            response.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");
            OutputStream out = response.getOutputStream();
            aService.export(sblsh,excelName ,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
