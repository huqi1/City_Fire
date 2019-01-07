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
        label="位置全称">
        <template slot-scope="scope">
          <el-button type="text" size="medium" @click="showinfor(scope.row.locationId)">{{scope.row.locationName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="city"
        header-align="center"
        align="center"
        min-width="40%"
        label="所属地市">
      </el-table-column>
      <el-table-column
        prop="district"
        header-align="center"
        align="center"
        min-width="35%"
        label="所属区县">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        min-width="35%"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 0" size="medium" type="success">正常</el-tag>
          <el-tag v-if="scope.row.status == 1" size="medium" type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="gmtCreate"
        header-align="center"
        align="center"
        min-width="65%"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        label="操作">
        <template slot-scope="scope">
          <el-button round v-if="isAuth('operate:category:update')" type="primary" size="mini" @click="showinfor(scope.row.locationId)">备注</el-button>
          <el-button round v-if="isAuth('operate:category:delete')" type="warning" size="mini" @click="stopUse(scope.row.locationId,1)">禁用</el-button>
          <el-button round v-if="isAuth('operate:category:delete')" type="danger" size="mini" @click="deleteHandle(scope.row.locationId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <ShowLocationInfo v-if="showLocationInfo" ref="showLocationInfo" @refreshDataList="getDataList"></ShowLocationInfo>
  </div>
</template>

<script>
  import ShowLocationInfo from  './location-info'
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
          url: this.$http.adornUrl('/location/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataList = data.page.list
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
