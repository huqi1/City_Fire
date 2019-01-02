<template>
  <div class="mod-menu">
    <el-form :inline="true" label-width="150px">
      <el-form-item label="设备名称：">
        <el-input v-model="dataForm.equipmentName" placeholder="输入设备名称" ></el-input>
      </el-form-item>
      <el-form-item label="选择分类：">
        <el-popover
          ref="menuListPopover"
          placement="bottom-start"
          trigger="click">
          <el-tree
            :data="menuList"
            :props="menuListTreeProps"
            node-key="categoryId"
            ref="menuListTree"
            @current-change="menuListTreeCurrentChangeHandle"
            :default-expand-all="true"
            :highlight-current="true"
            :expand-on-click-node="false">
          </el-tree>
        </el-popover>
        <el-input v-model="typeForm.typeName" :readonly="true" placeholder="点击选择所属分类" class="menu-list__input">
        </el-input>
      </el-form-item>
      <el-form-item label="数量(件)：">
        <el-input v-model="dataForm.equipmentNum" placeholder="输入设备数量"></el-input>
      </el-form-item>
    </el-form>
    <el-form :inline="true" label-width="150px">
      <el-form-item label="所在区：">
        <el-select v-model="dataForm.district"
                   @change="getlocationList(dataForm.district)"
                   placeholder="选择设备所在区" style="width:185px">
          <el-option
            v-for="item in districtList"
            :key="item"
            :value="item">
          </el-option>
        </el-select>

      </el-form-item>
      <el-form-item label="选择设备位置：">
        <el-select v-model="dataForm.locationName" readonly="readonly"  style="width:535px">
          <el-option
            v-for="item in locationList"
            :key="item.locationId"
            :value="item.locationName">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <el-form label-width="150px">
      <el-form-item label="确认设备编码：">
        <el-input v-model="dataForm.equipmentId" readonly="readonly" style="width:535px"></el-input>
        <el-button type="primary">生成编码</el-button>
      </el-form-item>
    </el-form>

    <el-form :inline="true" label-width="150px">
      <el-form-item label="小区：">
        <el-input v-model="dataForm.community" ></el-input>
      </el-form-item>
      <el-form-item label="单元：">
        <el-input v-model="dataForm.unit" ></el-input>
      </el-form-item>
      <el-form-item label="楼层：">
        <el-input v-model="dataForm.floor"></el-input>
      </el-form-item>
      <el-form-item label="楼道：">
        <el-input v-model="dataForm.corridor"></el-input>
      </el-form-item>
      <el-form-item label="房间号">
        <el-input v-model="dataForm.roomNumber" ></el-input>
      </el-form-item>
      <el-form-item label="管理员：">
        <el-input v-model="dataForm.administrator" ></el-input>
      </el-form-item>
      <el-form-item label="联系电话：">
        <el-input v-model="dataForm.phone" ></el-input>
      </el-form-item>
    </el-form>
    <el-form label-width="150px">
      <el-form-item label="备注信息：" style="width:1035px">
        <el-input
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4}"
          placeholder="请输入内容"
          v-model="dataForm.remark" ></el-input>
      </el-form-item>
    </el-form>
    <div >
      <el-button type="primary"  @click="updateRemark()">新增设备</el-button>
    </div>
    <!-- 弹窗, 新增 / 修改 -->
  <!--  <ShowLocationInfo v-if="showLocationInfo" ref="showLocationInfo" @refreshDataList="getDataList"></ShowLocationInfo>
-->
  </div>
</template>

<script>
 // import ShowLocationInfo from  './location-info'
 import { treeDataTranslate } from '@/utils'
  export default {
    data () {
      return {
        dataForm: {
          equipmentId: '',
          equipmentName: '',
          belongTypeid: '',
          belongTypename: '',
          equipmentPrice: '',
          equipmentNum: '',
          localtionId: '',
          locationName: '',
          equipmentStatus: '',
          locationStatus: '',
          community:'',
          unit:'',
          floor:'',
          corridor:'',
          roomNumber:'',
          administrator:'',
          phone:'',
          remark:''
        },
        locationList: [
          {
            locationId: '',
            locationName: '',
            province: '',
            city: '',
            citycode: '',
            district: '',
            adcode: '',
            street: '',
            streetNumber: '',
            lat: '',
            lng: '',
            status: '',
            gmtCreate: ''
          }
        ],
        dataListLoading: false,
        addOrUpdateVisible: false,
        showLocationInfo: false,
        menuList: [],
        menuListTreeProps: {
          label: 'typeName',
          children: 'children'
        },
        typeForm: {
          categoryId: '',
          typeName: '',
          typePid: '',
          typePname: '',
          typeId: ''
        },
        districtList: []
      }
    },
    components: {
    },
    activated () {
      this.gettypelist();
      this.getDistrictList();
    },
    methods: {
      gettypelist () {
        this.$http({
          url: this.$http.adornUrl('/category/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.menuList = treeDataTranslate(data.page.list, 'typeId' ,'typePid')
        })
      },
      // 菜单树选中
      menuListTreeCurrentChangeHandle (data, node) {
        this.typeForm.categoryId = data.typeId
        this.typeForm.typeName = data.typeName
        console.log("选中的id = "+this.typeForm.categoryId )
        console.log("选择的名称 = "+this.typeForm.typeName)
      },
      getDistrictList(){
        this.dataListLoading = true;
        this.$http({
          url:this.$http.adornUrl('/location/districtList'),
          method: 'get',
          params:this.$http.adornParams({
            citycode:"028"
          })
        }).then( ({data}) =>{
          this.districtList = data.page
          })
      },
      getlocationList(district){
        this.dataListLoading = true;
        this.$http({
          url:this.$http.adornUrl('/location/locationList'),
          method: 'get',
          params:this.$http.adornParams({
            district:district
          })
        }).then(({data}) => {
          this.locationList = data.page
        })
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
      },
      //禁用
      stopUse(id,status){
        let self = this
        self.$http({
          url:this.$http.adornUrl(`/location/updateStatus`),
          method:'post',
          params:this.$http.adornParams({
            locationid:id,
            status:status
          })
        }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          }
        )
      },
    }
  }
</script>
