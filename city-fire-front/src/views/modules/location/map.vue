<template>
  <div>
      <el-form :inline="true">
        <el-form-item label-width="30px">
          <el-amap-search-box class="search-box"

                              :search-option="searchOption"
                              :on-search-result="onSearchResult">
          </el-amap-search-box>
        </el-form-item>
        <el-form-item>
          <el-button v-if="isAuth('operate:map:save')" type="primary" @click="AddLocation()">新增</el-button>
        </el-form-item>
      </el-form>

      <div class="amap-wrapper">
        <el-amap vid="amap" :plugin="plugin"  :center="center" :zoom ="zoom">
          <div v-if=" markersflag == true ">
              <el-amap-marker  v-for="(marker,idx) in markers" :key= "idx" :position="marker.marker" :events="marker.events"></el-amap-marker>
            <el-amap-info-window v-if="windowitem" :position="windowitem.position" :visible="windowitem.visible" :content="windowitem.content"></el-amap-info-window>
          </div>
        </el-amap>
        <div class="toolbar">
        <span v-if="loaded">
          当前所在位置:  {{ locationinfor }}
        </span>
          <span v-else>正在定位</span>
        </div>
      </div>
    <!-- 弹窗, 新增Map -->
    <add-location v-if="addLocationVisible" ref="AddLocation" ></add-location>

  </div>
</template>

<script>
 import AddLocation from './map-add'

  export default {

    data () {
      let self = this;
      return {
        choesLocation:'',
        dataForm: {
          citycode:'',
          adcode:'',
          businessAreas:[{
            name:'',
            id:'',
            location:{
              P:'',
              O:'',
              lng:'',
              lat:''
            }
          }],
          neighborhoodType:'',
          neighborhood:'',
          building:'',
          buildingType:'',
          township:'',
          province:'',
          city:'',
          district:'',
          street:'',
          streetNumber:''
        },
        // 地图搜索
        searchOption: {
          city: '成都',
          citylimit: true
        },
        addLocationVisible: false,
        //地图
        locationinfor:'',
        center: [103.98291, 30.57531],
        zoom:15, // 放大比列
        lng: 0,
        lat: 0,
        loaded: false,
        plugin: [{
          pName: 'Geolocation',
          events: {
           init (o) {
              // o 是高德地图定位插件实例
             self.clearmarkers()
              o.getCurrentPosition((status, result) => {
                if (result && result.position) {
                  console.log("当前地址:"+result.formattedAddress)
                  self.locationinfor = result.formattedAddress;
                  self.choesLocation = result.formattedAddress;
                  self.dataForm = JSON.parse(JSON.stringify(result.addressComponent, null, 4));
                  //businessAreas可能为null
                  self.lng = result.position.lng;
                  self.lat = result.position.lat;
                  self.searchOption.city = self.dataForm.city
                  self.center = [self.lng, self.lat];
                  let marker = [self.lng, self.lat];
                  self.markers = [{
                    marker: marker
                  }
                  ];
                  self.markersflag = true;
                  self.loaded = true;
                  self.$nextTick();
                }
              });
            },


          }
        }],
        markersflag: false,
        markers: [],
        //地图窗口
        windows: [],
        windowitem: ''
      }
    },
    components: {
      AddLocation
    },
    activated () {
      //this.getDataList()
    },
    methods: {
      //地图搜索
      onSearchResult (pois) {
        this.clearmarkers()
        let latSum = 0;
        let lngSum = 0;
        let markerNum = 2;
        let windows = [];
        if (pois.length > 0) {
          if (pois.length < markerNum) {
            markerNum = pois.length
          }
          for (let i = 0;i<markerNum;i++){
            let poi = pois[i]
            console.log("[poi.lng:" + poi.lng)
            console.log("[poi.lat:" + poi.lat)
            let {lng, lat} = poi;
            lngSum += lng;
            latSum += lat;
            this.markers.push({
              marker : [poi.lng, poi.lat],
              events: {
                click () {
                  /*self.windows.forEach(window => {
                    window.visible = false;
                  });
                  self.window = self.windows[i];
                  self.$nextTick(() => {
                    self.window.visible = true;
                  });*/
                  console.log(self.windows[0])
                 /* let windowItem = self.windows[0];
                  self.windowitem = windowItem;*/

                }
              }
            });
          }
          let center = {
            lng: lngSum /markerNum ,
            lat: latSum /markerNum
          };
          this.center = [center.lng, center.lat];
          this.markersflag = true;
          this.setdataForm(center.lng, center.lat);
        }
      },
      clearmarkers (){
        this.markersflag = false
        this.markers = [];
      },
      //展示窗口
      showWindows () {
        this.windows = []
        this.dataForm = self.dataForm
        this.choesLocation = self.choesLocation
        this.windows.push({
          position:  this.center,
          content: `<div class="prompt">
                              <h3 style="text-align: center">所选位置信息</h3>
                              <p>省: ${this.dataForm.province}</p>
                              <p>市: ${this.dataForm.city}</p>
                              <p>区: ${this.dataForm.district}</p>
                              <p>街道: ${this.dataForm.street}</p>
                              <p>详情: ${this.choesLocation}</p>
                            </div>`,
          visible: true
        });
        this.windowitem = this.windows[0];
      },
      // 新增 / 修改
      AddLocation () {
        this.addLocationVisible = true
        this.$nextTick(() => {
          this.$refs.AddLocation.init(this.dataForm,this.choesLocation)
        })
      },

      setdataForm(lng,lat){
        let tself = this;
        var geocoder = new AMap.Geocoder({
          radius: 1000,
          extensions: "all"
        });
        geocoder.getAddress([lng ,lat], (status, result) =>{
          if (status === 'complete' && result.info === 'OK') {
            if (result && result.regeocode) {
              console.log("当前搜索地址:" + result.regeocode.formattedAddress)
              console.log("当前搜索详情:" + JSON.stringify(result.regeocode.addressComponent, null, 4))
              self.locationinfor =result.regeocode.formattedAddress
              self.choesLocation = result.regeocode.formattedAddress
              self.dataForm = JSON.parse(JSON.stringify(result.regeocode.addressComponent, null, 4));
              if (self.dataForm.businessAreas.length == 0){
                let supply = {
                  name: '',
                  id: '',
                  location: {
                    P: '',
                    O: '',
                    lng: lat,
                    lat: lng
                  }
                }
                self.dataForm.businessAreas.push(supply)
              }
              console.log("修改搜索详情:" + JSON.stringify(self.dataForm, null, 4))
              tself.showWindows()
            }
          }
        });
      }
    }
  }



</script>
<style>
  .amap-wrapper {
    position:absolute;
    top:75px;
    left:25px;
    width:96%;
    height:81%;
  }
  .search-box {
    position:absolute;
    height: 55px;
  }
  .prompt {
    background: white;
    width: 180px;
    height: 170px;
    text-align: left;
    line-height:14px;
  }
</style>
