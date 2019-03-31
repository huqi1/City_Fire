<template>
  <div class="mod-menu">
    <el-form :inline="true" :model="searchData" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="searchData.equipmentName" placeholder="设备名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
      <el-select v-model="searchData.belongTypeName"
                 placeholder="可选择设备所属分类"
                 style="width:185px">
        <el-option
          v-for="item in BelongTypeList"
          :key="item"
          :value="item">
        </el-option>
      </el-select>
    </el-form-item>
      <el-form-item>
        <el-select v-model="searchData.community"
                   placeholder="可选择设备所属小区" style="width:185px">
          <el-option
            v-for="item in communityList"
            :key="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()" type="primary">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="exportdata('','','当前页')" type="primary">导出本页</el-button>
      </el-form-item>
      <el-form-item style="padding-right: 0px">
        <el-button @click="exportdata(1,20,'所有页')" type="primary">导出所有</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      style="width: 100%;">
      <el-table-column
        header-align="center"
        align="center"
        min-width="40%"
        label="设备名称">
        <template slot-scope="scope">
          <el-button type="text" size="medium" @click="showinfor(scope.row.equipmentId)">{{scope.row.equipmentName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="equipmentId"
        header-align="center"
        align="center"
        min-width="110%"
        label="设备编码">
      </el-table-column>
      <el-table-column
        prop="locationStatus"
        header-align="center"
        align="center"
        min-width="40%"
        label="使用状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.locationStatus == 0" size="medium" type="success">在库</el-tag>
          <el-tag v-if="scope.row.locationStatus == 1" size="medium" type="danger">检修</el-tag>
          <el-tag v-if="scope.row.locationStatus == 2" size="medium" type="info">在用</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="equipmentStatus"
        header-align="center"
        align="center"
        min-width="40%"
        label="设备状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.equipmentStatus == 0" size="medium" type="success">正常</el-tag>
          <el-tag v-if="scope.row.equipmentStatus == 1" size="medium" type="warning">报修</el-tag>
          <el-tag v-if="scope.row.equipmentStatus == 2" size="medium" type="danger">报废</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="gmtCreate"
        header-align="center"
        align="center"
        min-width="70%"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        label="操作">
        <template slot-scope="scope">
          <el-button round v-if="isAuth('operate:equipment:update')" type="primary" size="mini" @click="showinfor(scope.row.equipmentId)">修改</el-button>
          <el-button round v-if="isAuth('operate:equipment:delete')" type="danger" size="mini" @click="deleteHandle(scope.row.equipmentId)">删除</el-button>
          <el-button round v-if="isAuth('operate:equipment:update')" type="warning" size="mini" @click="changeState(scope.row.equipmentId)">状态</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <ShowEquipmentInfo v-if="showEquipmentInfo" ref="showEquipmentInfo" @refreshDataList="getDataList"></ShowEquipmentInfo>
    <ChangeState v-if="ChangeState" ref="ChangeState" @refreshDataList="getDataList"></ChangeState>
  </div>
</template>

<script>
  import ShowEquipmentInfo from  './equipment-info'
  import ChangeState from  './equipment-change-state'
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
          remark:'',
          gmtCreate:''
        },
        dataList: [
          {
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
            remark:'',
            gmtCreate:''
          }
        ],
        searchData:{
          equipmentName:'',
          belongTypeName:'',
          community:''
        },
        BelongTypeList:[],
        communityList:[],
        dataListLoading: false,
        addOrUpdateVisible: false,
        showEquipmentInfo:false,
        ChangeState:false,
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0
      }
    },
    components: {
      ShowEquipmentInfo,
      ChangeState
    },
    activated () {
      this.getDataList()
      this.getTypelistList()
      this.getCommunitylistList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/equipment/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'equipmentName':this.searchData.equipmentName,
            'belongTypeName':this.searchData.belongTypeName,
            'community':this.searchData.community
          })
        }).then(({data}) => {
          this.dataList = data.page.list
          this.dataListLoading = false
          this.totalPage = data.page.totalCount
        })
      },
      // 获取数据列表
      exportdata (page,limit,exportFlag) {
        this.dataListLoading = true
        var FileName = ""
        if(exportFlag =='所有页'){
          FileName = "所有设备信息-";
        }else{
          FileName = "当前页设备信息-"
          page = this.pageIndex
          limit = this.pageSize
        }
        FileName = FileName+new Date().getTime()+".xlsx"
        this.$http({
          url: this.$http.adornUrl('/equipment/export'),
          method: 'get',
          params: this.$http.adornParams({
            'page': page,
            'limit': limit,
            'equipmentName':this.searchData.equipmentName,
            'belongTypeName':this.searchData.belongTypeName,
            'community':this.searchData.community
          }),
          responseType: 'blob' // 这一步也很关键，一定要加上 responseType 值为 blob
        }).then(({data}) => {
          if (!data) {
            return
          }
          let url = window.URL.createObjectURL(new Blob([data]))
          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = url

          // download 属性定义了下载链接的地址而不是跳转路径
          link.setAttribute('download', FileName)
          document.body.appendChild(link)
          link.click()
        })
      },
      showinfor(equipmentId){
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl(`/equipment/info/${equipmentId}`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataForm = data.cfEquipment
          this.dataListLoading = false
          this.showEquipmentInfo = true
          this.$nextTick(() => {
            this.$refs.showEquipmentInfo.init(this.dataForm)
          })
        })
      },
      changeState(equipmentId){
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl(`/equipment/info/${equipmentId}`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataForm = data.cfEquipment
          this.dataListLoading = false
          this.ChangeState = true
          this.$nextTick(() => {
            this.$refs.ChangeState.init(this.dataForm)
          })
        })
      },
      //获取所有存在的类型信息
      getTypelistList(){
        this.dataListLoading = true;
        this.$http({
          url:this.$http.adornUrl('/equipment/typelist'),
          method: 'get',
          params:this.$http.adornParams({
          })
        }).then( ({data}) =>{
          this.BelongTypeList = data.page
        })
      },
      //获取所有存在的类型信息
      getCommunitylistList(){
        this.dataListLoading = true;
        this.$http({
          url:this.$http.adornUrl('/equipment/communitylist'),
          method: 'get',
          params:this.$http.adornParams({
          })
        }).then( ({data}) =>{
          this.communityList = data.page
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
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
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
            url: this.$http.adornUrl(`/equipment/delete`),
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
