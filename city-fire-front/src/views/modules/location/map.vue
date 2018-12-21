<template>
  <div>
    <div>
      <el-form :inline="true">
        <el-form-item>
          <el-button v-if="isAuth('operate:map:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
      <div class="amap-wrapper">
        <el-amap-search-box class="search-box"
                            :search-option="searchOption"
                            :on-search-result="onSearchResult">
        </el-amap-search-box>
        <el-amap vid="amap" :plugin="plugin"  :center="center">
          <div v-if=" markersflag == true ">
              <el-amap-marker  v-for="marker in markers" :position="marker" ></el-amap-marker>
          </div>
        </el-amap>
        <div class="toolbar">
        <span v-if="loaded">
          位置: 纬度 = {{ lng }} 经度 = {{ lat }}
        </span>
          <span v-else>正在定位</span>
        </div>
      </div>
   </div>
</template>

<script>
  import TableTreeColumn from '@/components/table-tree-column'
  import AddOrUpdate from './location-add-or-update'
  import { treeDataTranslate } from '@/utils'

  export default {
    data () {
      let self = this;
      return {
        dataForm: {},
        dataList: [],
        dataListLoading: false,
        addOrUpdateVisible: false,
        //地图
        center: [103.98291, 30.57531],
        lng: 0,
        lat: 0,
        loaded: false,
        plugin: [{
          pName: 'Geolocation',
          events: {

            init (o) {
              // o 是高德地图定位插件实例
              o.getCurrentPosition((status, result) => {
                if (result && result.position) {
                  self.lng = result.position.lng;
                  self.lat = result.position.lat;
                  self.center = [self.lng, self.lat];
                  self.markers = [[self.lng, self.lat]];
                  self.markersflag = true;
                  console.log("当前位置：纬度："+self.lng,"经度"+self.lat);
                  self.loaded = true;
                  self.$nextTick();
                }
              });
            }
          }
        }],
        // 地图搜索
        searchOption: {
          city: '成都',
          citylimit: true
        },
        markersflag: false,
        markers: [
        ],
      }
    },
    components: {
      TableTreeColumn,
      AddOrUpdate
    },
    activated () {
      //this.getDataList()
    },
    methods: {
      //地图搜索
      onSearchResult (pois) {
        let latSum = 0;
        let lngSum = 0;
        if (pois.length > 0) {
          pois.forEach(poi => {
            let {lng, lat} = poi;
            lngSum += lng;
            latSum += lat;
            this.markers.push([poi.lng, poi.lat]);
          });
          let center = {
            lng: lngSum / pois.length,
            lat: latSum / pois.length
          };
        this.center = [center.lng, center.lat];
        }
      },
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/category/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataList = treeDataTranslate(data.page.list, 'typeId', 'typePid')
          this.dataListLoading = false
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
<style>
  .amap-wrapper {
    position:absolute;
    top:55px;
    left:25px;
    width:96%;
    height:81%;
  }
  .search-box {
    position:absolute;
  }
</style>
