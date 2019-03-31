package com.hq.modules.operate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hq.common.utils.DateUtils;
import com.hq.common.utils.PageUtils;
import com.hq.common.utils.PoiUtils;
import com.hq.common.utils.R;
import com.hq.common.validator.ValidatorUtils;
import com.hq.modules.operate.entity.EquipmentEntity;
import com.hq.modules.operate.service.EquipmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


@RestController
@RequestMapping("equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
    @Value("${spring.file.basefileroot}")
    private  String  FILEROOTBASE;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("operate:equipment:list")
    public R list(@RequestParam Map<String, Object> params){
        params.put("sidx","gmt_create");
        params.put("order","DESC");
        PageUtils page = equipmentService.queryPage(params);

        Map<String,Object> map = new HashMap<>();
        map.put("page", page);
        return R.ok(map);
    }

    /**
     * 查询所有已存在的类型
     */
    @RequestMapping("/typelist")
    @RequiresPermissions("operate:equipment:list")
    public R typelist(@RequestParam Map<String, Object> params){

        List<String> typelist = equipmentService.selectAllType();
        Map<String,Object> map = new HashMap<>();
        map.put("page", typelist);
        return R.ok(map);
    }

    /**
     * 查询所有已存在的小区
     */

    @RequestMapping("/communitylist")
    @RequiresPermissions("operate:equipment:list")
    public R communitylist(@RequestParam Map<String, Object> params){

        List<String> typelist = equipmentService.selectAllCommunity();
        Map<String,Object> map = new HashMap<>();
        map.put("page", typelist);
        return R.ok(map);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{equipmentId}")
    @RequiresPermissions("operate:equipment:info")
    public R info(@PathVariable("equipmentId") String equipmentId){
        EquipmentEntity cfEquipment = equipmentService.selectById(equipmentId);

        return R.ok().put("cfEquipment", cfEquipment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("operate:equipment:save")
    public R save(@RequestBody EquipmentEntity cfEquipment){
        cfEquipment.setEquipmentStatus(0);
        cfEquipment.setLocationStatus(0);
        cfEquipment.setGmtCreate(new Date());
        equipmentService.insertAllColumn(cfEquipment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("operate:equipment:update")
    public R update(@RequestBody EquipmentEntity cfEquipment){
        ValidatorUtils.validateEntity(cfEquipment);
        equipmentService.updateAllColumnById(cfEquipment);//全部更新
        
        return R.ok();
    }

    /**
     * 修改by oldid
     */
    @RequestMapping("/updatebyid")
    @RequiresPermissions("operate:equipment:update")
    public R update(@RequestParam Map<String,Object> map){
        EquipmentEntity equipment  = JSON.parseObject((String) map.get("equipment"),new TypeReference<EquipmentEntity>(){});
        String id = (String) map.get("id");
        //ValidatorUtils.validateEntity(cfEquipment);
        equipmentService.updateAllByid(equipment,id);//全部更新
        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("operate:equipment:delete")
    public R delete(@RequestBody String[] equipmentIds){
        equipmentService.deleteBatchIds(Arrays.asList(equipmentIds));

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/export")
    @RequiresPermissions("operate:equipment:list")
    public void export(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
        params.put("sidx","gmt_create");
        params.put("order","DESC");
        PageUtils page = equipmentService.queryPage(params);
        List<EquipmentEntity> equipmentList = (List<EquipmentEntity>) page.getList();
        String sheetName = "";
        String fileName = "";
        if(params.get("page").equals("1") && params.get("limit").equals("20")){
            sheetName = "设备信息-所有页位置数据"+DateUtils.getNowTimetohm();
        }else {
            sheetName = "设备信息-当前页位置数据"+DateUtils.getNowTimetohm();
        }
        String sheetTitle  = "设备信息数据";
        fileName = sheetName;
        List<String> columnNames = new LinkedList<>();
        columnNames.add("设备id");
        columnNames.add("设备名称");
        columnNames.add("所属分类id");
        columnNames.add("所属分类名称");
        columnNames.add("设备数量");
        columnNames.add("设备状态");
        columnNames.add("设备所在位置id");
        columnNames.add("设备所在位置名称");
        columnNames.add("小区");
        columnNames.add("单元");
        columnNames.add("楼层");
        columnNames.add("楼道");
        columnNames.add("房间号");
        columnNames.add("管理员");
        columnNames.add("电话");
        columnNames.add("创建时间");

        //写入标题--第二种方式
        try {
            PoiUtils.writeExcelTitle(FILEROOTBASE, fileName, sheetName, columnNames, sheetTitle, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> data = new LinkedList<>();
        int size = equipmentList.size();
        for (int i = 0; i < size; i++) {
            List<Object> dataA = new LinkedList<>();
            dataA.add(equipmentList.get(i).getEquipmentId());
            dataA.add(equipmentList.get(i).getEquipmentName());
            dataA.add(equipmentList.get(i).getBelongTypeid());
            dataA.add(equipmentList.get(i).getBelongTypename());
            dataA.add(equipmentList.get(i).getEquipmentNum());
            if (equipmentList.get(i).getEquipmentStatus() == 0) {
                dataA.add("正常");
            }
            else if(equipmentList.get(i).getEquipmentStatus() == 2){
                dataA.add("报废");
            }else {
                dataA.add("报修");
            }
            dataA.add(equipmentList.get(i).getLocaltionId());
            dataA.add(equipmentList.get(i).getLocationName());
            dataA.add(equipmentList.get(i).getCommunity());
            dataA.add(equipmentList.get(i).getUnit());
            dataA.add(equipmentList.get(i).getFloor());
            dataA.add(equipmentList.get(i).getCorridor());
            dataA.add(equipmentList.get(i).getRoomNumber());
            dataA.add(equipmentList.get(i).getAdministrator());
            dataA.add(equipmentList.get(i).getPhone());
            dataA.add(equipmentList.get(i).getGmtCreate());
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
}
