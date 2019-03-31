<template>
  <div class="mod-menu">
    <el-form :inline="true" :model="searchData" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="searchData.equipmentName" placeholder="设备名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
      <el-select v-model="searchData.operatorStatus"
                 placeholder="可选择报警项目"
                 style="width:185px">
        <el-option
          v-for="item in equipmentStatusList"
          :key="item.key"
          :value="item.key"
          :label="item.value">
        </el-option>
      </el-select>
    </el-form-item>
      <el-form-item>
        <el-select v-model="searchData.status"
                   placeholder="可选择处理情况" style="width:185px">
          <el-option
            v-for="item in dealStatusList"
            :key="item.key"
            :value="item.key"
            :label="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()" type="primary">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="exportdata ('','','当前页')" type="primary">导出本页</el-button>
      </el-form-item>
      <el-form-item style="padding-right: 0px">
        <el-button @click="exportdata (1,20,'所有页')" type="primary">导出所有</el-button>
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
          <el-button type="text" size="medium" @click="showEquipmentinfor(scope.row.equipmentId)">{{scope.row.equipmentName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="equipmentId"
        header-align="center"
        align="center"
        min-width="120%"
        label="设备编码">
      </el-table-column>
      <el-table-column
        prop="operatorStatus"
        header-align="center"
        align="center"
        min-width="40%"
        label="报警项目">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.operatorStatus == 1" size="medium" type="warning">报修</el-tag>
          <el-tag v-if="scope.row.operatorStatus == 2" size="medium" type="danger">报废</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        min-width="40%"
        label="处理情况">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 0" size="medium" type="success">已处理</el-tag>
          <el-tag v-if="scope.row.status == 1" size="medium" type="danger">未处理</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="warningTime"
        header-align="center"
        align="center"
        min-width="70%"
        label="报警时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        label="操作">
        <template slot-scope="scope">
          <el-button round v-if="isAuth('operate:warningrecord:info') && scope.row.status == 1 " type="primary" size="mini" @click="showwarninginfor(scope.row.recordId,scope.row.equipmentId,false)">处理</el-button>
          <el-button round v-if="isAuth('operate:warningrecord:info') && scope.row.status == 0 " type="success" size="mini" @click="showwarninginfor(scope.row.recordId,scope.row.equipmentId,true)">详情</el-button>
          <el-button round v-if="isAuth('operate:warningrecord:delete')" type="danger" size="mini" @click="deleteHandle(scope.row.recordId)">删除</el-button>
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
    <ShowWarningInfo v-if="ShowWarningInfo" ref="ShowWarningInfo" @refreshDataList="getDataList"></ShowWarningInfo>
    <ShowEquipmentInfo v-if="ShowEquipmentInfo" ref="ShowEquipmentInfo" @refreshDataList="getDataList"></ShowEquipmentInfo>
  </div>
</template>

<script>
  import ShowWarningInfo from  './warning-info'
  import ShowEquipmentInfo from  './equipment-info'
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
            recordId:'',
            equipmentId:'',
            equipmentName:'',
            warningOperator:'', //报修人
            warningReason:'', //报修原因
            operatorStatus:'',//操作项
            warningTime:'',
            status:'',
            dealOperator:'', //处理人
            dealTime:'', //处理时间
            dealPlan:'' //处理方案
          }
        ],
        WarningFrom:{
          recordId:'',
          equipmentId:'',
          equipmentName:'',
          warningOperator:'', //报修人
          warningReason:'', //报修原因
          operatorStatus:'',//操作项
          warningTime:'',
          status:'',
          dealOperator:'', //处理人
          dealTime:'', //处理时间
          dealPlan:'' //处理方案
        },
        dealStatusList:[{
          key:0,
          value:"已解决"
        },
          {
            key:1,
            value:"未解决"
          }],
        equipmentStatusList:[
          {
            key: '1',
            value: '报修'
          },
          {
            key: '2',
            value: '报废'
          }
        ],
        searchData:{
          equipmentName:'',
          operatorStatus:'',
          status:''
        },
        dealStatusList:[{
          key:0,
          value:"已处理"
        },
          {
            key:1,
            value:"未处理"
          }],

        dataListLoading: false,
        addOrUpdateVisible: false,
        ShowWarningInfo:false,
        ShowEquipmentInfo:false,
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0
      }
    },
    components: {
      ShowWarningInfo,
      ShowEquipmentInfo
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/report/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'equipmentName':this.searchData.equipmentName,
            'operatorStatus':this.searchData.operatorStatus,
            'status':this.searchData.status
          })
        }).then(({data}) => {
          this.dataList = data.page.list
          this.dataListLoading = false
          this.totalPage = data.page.totalCount
        })
      },
      showEquipmentinfor(equipmentId){
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl(`/equipment/info/${equipmentId}`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataForm = data.cfEquipment
          this.dataListLoading = false
          this.ShowEquipmentInfo = true
          this.$nextTick(() => {
            this.$refs.ShowEquipmentInfo.init(this.dataForm)
          })
        })
      },
      showwarninginfor(recordId,equipmentId,isDeal){
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl(`/report/info/${recordId}`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.WarningFrom = data.cfWarningrecord
          this.dataListLoading = false
          this.ShowWarningInfo = true
          this.$nextTick(() => {
            this.$refs.ShowWarningInfo.init(this.WarningFrom,equipmentId,isDeal)
          })
        })
      },
      // 获取数据列表
      exportdata (page,limit,exportFlag) {
        this.dataListLoading = true
        var FileName = ""
        if(exportFlag =='所有页'){
          FileName = "所有报警信息-";
        }else{
          FileName = "当前页报警信息-"
          page = this.pageIndex
          limit = this.pageSize
        }
        FileName = FileName+new Date().getTime()+".xlsx"
        this.$http({
          url: this.$http.adornUrl('/report/export'),
          method: 'get',
          params: this.$http.adornParams({
            'page': page,
            'limit': limit,
            'equipmentName':this.searchData.equipmentName,
            'operatorStatus':this.searchData.operatorStatus,
            'status':this.searchData.status
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
            url: this.$http.adornUrl(`/report/delete`),
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
