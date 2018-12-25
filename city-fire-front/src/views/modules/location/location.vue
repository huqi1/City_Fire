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
        label="所属地市">
      </el-table-column>
      <el-table-column
        prop="district"
        header-align="center"
        align="center"
        label="所属区县">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
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
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('operate:category:update')" type="primary" size="medium" @click="addOrUpdateHandle(scope.row.categoryId)">修改</el-button>
          <el-button v-if="isAuth('operate:category:delete')" type="danger" size="medium" @click="deleteHandle(scope.row.categoryId,scope.row.typeName)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <ShowLocationInfo v-if="showLocationInfo" ref="showLocationInfo"></ShowLocationInfo>
  </div>
</template>

<script>
  import AddOrUpdate from './location-add-or-update'
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
      AddOrUpdate,
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
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id, name) {
        this.$confirm(`确定对名称为【${name}】进行【删除】操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/category/delete`),
            method: 'post',
            params: this.$http.adornParams({
              categoryId: id
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
          })
        }).catch(() => {})
      }
    }
  }
</script>
