<template>
  <div class="mod-menu">
    <el-form :inline="true" :model="dataForm">
    </el-form>
    <el-table
      :data="dataList"
      border
      style="width: 100%;">
      <el-table-column
        header-align="center"
        align="center"
        min-width="120%"
        label="设备名称">
        <template slot-scope="scope">
          <el-button type="text" size="medium" @click="showinfor(scope.row.equipmentId)">{{scope.row.equipmentName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="equipmentId"
        header-align="center"
        align="center"
        min-width="40%"
        label="设备编码">
      </el-table-column>
      <el-table-column
        prop="locationStatus"
        header-align="center"
        align="center"
        min-width="40%"
        label="使用状态">
      </el-table-column>
      <el-table-column
        prop="equipmentStatus"
        header-align="center"
        align="center"
        min-width="40%"
        label="设备状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 0" size="medium" type="success">正常</el-tag>
          <el-tag v-if="scope.row.status == 1" size="medium" type="danger">禁用</el-tag>
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
          <el-button round v-if="isAuth('operate:equipment:update')" type="primary" size="mini" @click="showinfor(scope.row.locationId)">修改</el-button>
          <el-button round v-if="isAuth('operate:equipment:delete')" type="danger" size="mini" @click="deleteHandle(scope.row.locationId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <ShowLocationInfo v-if="showLocationInfo" ref="showLocationInfo" @refreshDataList="getDataList"></ShowLocationInfo>
  </div>
</template>

<script>
  import ShowLocationInfo from  './equipment-info'
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
        dataListLoading: false,
        addOrUpdateVisible: false,
        showLocationInfo:false
      }
    },
    components: {
      ShowLocationInfo
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/equipment/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          console.log("data == "+JSON.stringify(data))
          this.dataList = data.page.list
          console.log("list == "+JSON.stringify(this.dataList))
          this.dataListLoading = false
        })
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
