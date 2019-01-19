<template>
  <div class="mod-menu">
    <el-form :inline="true" :model="searchData" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="searchData.locationName" placeholder="设备名称" clearable></el-input>
      </el-form-item>
      <el-form-item prop="district">
        <el-select v-model="searchData.district"
                   placeholder="可选择位置所在区" style="width:185px">
          <el-option
            v-for="item in districtList"
            :key="item"
            :value="item">
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
        <el-button @click="exportdata (1,2000,'所有页')" type="primary">导出所有</el-button>
      </el-form-item>
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
          <el-button round v-if="isAuth('operate:location:updateRemark') && scope.row.status == 0" type="warning" size="mini" @click="stopUse(scope.row.locationId,1)">禁用</el-button>
          <el-button round v-if="isAuth('operate:location:updateRemark') && scope.row.status == 1" type="success" size="mini" @click="stopUse(scope.row.locationId,0)">恢复</el-button>
          <el-button round v-if="isAuth('operate:category:delete')" type="danger" size="mini" @click="deleteHandle(scope.row.locationId)">删除</el-button>
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
    <ShowLocationInfo v-if="showLocationInfo" ref="showLocationInfo" @refreshDataList="getDataList"></ShowLocationInfo>
  </div>
</template>

<script>
  import ShowLocationInfo from  './location-info'
  export default {
    data () {
      return {
        searchData:{
          locationName:'',
          district:''
        },
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
        districtList: [],
        dataListLoading: false,
        addOrUpdateVisible: false,
        showLocationInfo:false,
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0
      }
    },
    components: {
      ShowLocationInfo
    },
    activated () {
      this.getDataList()
      this.getDistrictList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/location/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'district': this.searchData.district,
            'locationName':this.searchData.locationName
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
          FileName = "所有地区信息-";
        }else{
          FileName = "已选地区信息-"
          page = this.pageIndex
          limit = this.pageSize
        }
        FileName = FileName+new Date().getTime()+".xlsx"
        this.$http({
          url: this.$http.adornUrl('/location/export'),
          method: 'get',
          params: this.$http.adornParams({
            'page': page,
            'limit': limit,
            'district': this.searchData.district,
            'locationName':this.searchData.locationName
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
      //获取所有区信息
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
