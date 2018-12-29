<template>
  <div class="mod-menu">
    <el-form :inline="true" label-width="150px">
      <el-form-item label="设备名称：">
        <el-input v-model="dataForm.province" placeholder="输入设备名称" ></el-input>
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
        <el-input v-model="dataForm.citycode" placeholder="输入设备数量"></el-input>
      </el-form-item>
    </el-form>
    <el-form :inline="true" label-width="150px">
      <el-form-item label="所在区：">
        <el-input v-model="dataForm.locationName" readonly="readonly"  style="width:185px"></el-input>
      </el-form-item>
      <el-form-item label="选择设备位置：">
        <el-input v-model="dataForm.locationName" readonly="readonly"  style="width:535px"></el-input>
      </el-form-item>
    </el-form>
    <el-form label-width="150px">
      <el-form-item label="确认设备编码：">
        <el-input v-model="dataForm.locationName" readonly="readonly" style="width:535px"></el-input>
        <el-button type="primary">生成编码</el-button>
      </el-form-item>
    </el-form>

    <el-form :inline="true" label-width="150px">
      <el-form-item label="小区：">
        <el-input v-model="dataForm.district" ></el-input>
      </el-form-item>
      <el-form-item label="单元：">
        <el-input v-model="dataForm.adcode" ></el-input>
      </el-form-item>
      <el-form-item label="楼层：">
        <el-input v-model="dataForm.street"></el-input>
      </el-form-item>
      <el-form-item label="楼道：">
        <el-input v-model="dataForm.streetNumber"></el-input>
      </el-form-item>
      <el-form-item label="房间号">
        <el-input v-model="dataForm.lat" ></el-input>
      </el-form-item>
      <el-form-item label="管理员：">
        <el-input v-model="dataForm.lng" ></el-input>
      </el-form-item>
      <el-form-item label="联系电话：">
        <el-input v-model="dataForm.lng" ></el-input>
      </el-form-item>
    </el-form>
    <el-form label-width="150px">
      <el-form-item label="备注信息：">
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
          locationId:'',
          locationName:'',
          province: '',
          city: '',
          citycode: '',
          district: '',
          adcode: '',
          street: '',
          streetNumber: '',
          lat:'',
          lng:'',
          status:'',
          gmtCreate:''
        },
        dataList: [
          {
            locationId:'',
            locationName:'',
            province: '',
            city: '',
            citycode: '',
            district: '',
            adcode: '',
            street: '',
            streetNumber: '',
            lat:'',
            lng:'',
            status:'',
            gmtCreate:''
          }
        ],
        dataListLoading: false,
        addOrUpdateVisible: false,
        showLocationInfo:false,
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
      }
    },
    components: {
    },
    activated () {
      this.getDataList()
      this.gettypelist()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/location/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataList = data.page.list
          this.dataListLoading = false
        })
      },
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
      showinfor(locationId){
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl(`/location/info/${locationId}`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataForm = data.cfLocation
          this.dataListLoading = false
          this.showLocationInfo =true
          this.$nextTick(() => {
            this.$refs.showLocationInfo.init(this.dataForm)
          })
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
      // 删除
      deleteHandle (id) {
        var ids = new Array()
        ids[0] = id
        this.$confirm(`确定对其进行【删除】操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/location/delete`),
            method: 'post',
            data: this.$http.adornData(ids,false)
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
          })
        }).catch(() => {})
      }
    }
  }
</script>
