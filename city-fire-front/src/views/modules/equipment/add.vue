<template>
  <div class="mod-menu">
    <el-form :inline="true" :model="dataForm"  ref="dataForm" :rules="dataRules" label-width="150px">
      <el-form-item label="设备名称：" prop="equipmentName">
        <el-input v-model="dataForm.equipmentName" placeholder="输入设备名称" ></el-input>
      </el-form-item>
      <el-form-item label="选择分类：" prop="belongTypename">
        <el-popover
          ref="menuListPopover"
          placement="bottom-start"
          trigger="click">
          <el-tree
            :data="menuList"
            :props="menuListTreeProps"
            node-key="categoryId"
            @current-change="menuListTreeCurrentChangeHandle"
            :default-expand-all="true"
            :highlight-current="true"
            :expand-on-click-node="false">
          </el-tree>
        </el-popover>
        <el-input v-model="dataForm.belongTypename" v-popover:menuListPopover :readonly="true" placeholder="点击选择所属分类" class="menu-list__input">
        </el-input>
      </el-form-item>
      <el-form-item label="数量(件)：" prop="equipmentNum">
        <el-input v-model="dataForm.equipmentNum" placeholder="输入设备数量"></el-input>
      </el-form-item>
    </el-form>
    <el-form :inline="true" :rules="dataRules"  ref="dataForm" :model="dataForm" label-width="150px">
      <el-form-item label="所在区：" prop="district">
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
      <el-form-item label="选择设备位置：" prop="locationName">
        <el-select v-model="dataForm.locationName"
                    readonly="readonly"
                   @change="setlocaltionInfor"
                   style="width:535px">
          <el-option
            v-for="(item,index) in locationList"
            :key="item.locationId"
            :value="item.locationName">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <el-form label-width="150px" :rules="dataRules"  ref="dataForm" :model="dataForm">
      <el-form-item label="确认设备编码：" prop="equipmentId">
        <el-input v-model="dataForm.equipmentId" readonly="readonly" style="width:535px"></el-input>
        <el-button @click="getequipmentId" type="primary">生成编码</el-button>
      </el-form-item>
    </el-form>

    <el-form :inline="true" label-width="150px" :rules="dataRules" ref="dataForm" :model="dataForm">
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
      <el-form-item label="管理员：" prop="administrator">
        <el-input v-model="dataForm.administrator" ></el-input>
      </el-form-item>
      <el-form-item label="联系电话：" prop="phone">
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
      <el-button type="primary"  @click="save()">新增设备</el-button>
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
        districtList: [],
        dataRules:{
          equipmentName :[
            {required: true, message: '设备名称不能为空', trigger: 'blur' }
          ],
          belongTypename :[
            {required: true, message: '设备所属分类不能为空', trigger: 'change' }
          ],
          equipmentNum :[
            {required: true, message: '设备数量不能为空', trigger: 'blur' }
          ],
          district :[
            {required: true, message: '设备所属区县不能为空', trigger: 'blur' }
          ],
          locationName :[
            {required: true, message: '设备位置不能为空', trigger: 'blur' }
          ],
          equipmentId :[
            {required: true, message: '设备编码不能为空', trigger: 'blur' }
          ],
          administrator :[
            {required: true, message: '管理员姓名不能为空', trigger: 'blur' }
          ],
          phone :[
            {required: true, message: '管理员电话不能为空', trigger: 'blur' }
          ],
        }
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
        this.dataForm.belongTypeid = data.typeId
        this.dataForm.belongTypename = data.typeName
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
      setlocaltionInfor(){
        let obj = {
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
        var index = this.locationList.findIndex((item)=>{
        //根据item中的id属性来判断这个item是否是上面id中
        //对应的数据，如果是返回一个true ,否返回false,继续下面的一条数据的遍历，以此类推
          return (item.locationName == this.dataForm.locationName); //如果返回true，那么findIndex方法会将这个item对应的id返回到外面接受
        });
        console.log("选中的位置id = "+this.locationList[index].locationId )
        this.dataForm.localtionId = this.locationList[index].locationId;
        console.log("选中的位置id = "+this.dataForm.localtionId )
        console.log("选择的位置名称 = "+this.dataForm.locationName)
      },
      getequipmentId(){
        if (this.dataForm.belongTypeid == null || this.dataForm.belongTypeid ===''){
          this.$message.error("请选择设备所属分类")
          return;
        }else if(this.dataForm.localtionId == null ||this.dataForm.localtionId === ''){
          this.$message.error("请选择设备所属位置")
          return;
        }else {
          var id = 'EQ-'+this.dataForm.belongTypeid+'-'+this.dataForm.localtionId+'-'+this.getUUID()
          this.dataForm.equipmentId = (id.toUpperCase()).replace("\.","-")
        }
      },
      getUUID(){
          return (((1 + Math.random()) * 1000000)).toString(16);
      },
      save(){
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.dataListLoading = true
            this.$http({
              url: this.$http.adornUrl('/equipment/save'),
              method:'post',
              data:this.$http.adornData({
                  'equipmentId': this.dataForm.equipmentId,
                  'equipmentName': this.dataForm.equipmentName,
                  'belongTypeid': this.dataForm.belongTypeid,
                  'belongTypename': this.dataForm.belongTypename,
                  'equipmentPrice': this.dataForm.equipmentPrice,
                  'equipmentNum': this.dataForm.equipmentNum,
                  'localtionId': this.dataForm.localtionId,
                  'locationName':this.dataForm.locationName,
                  'community': this.dataForm.community,
                  'unit': this.dataForm.unit,
                  'floor': this.dataForm.floor,
                  'corridor': this.dataForm.corridor,
                  'roomNumber': this.dataForm.roomNumber,
                  'administrator': this.dataForm.administrator,
                  'phone':this.dataForm.phone}
                ,false)
            }).then(({data})=>{
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.dataFormCancel()
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      },
      dataFormCancel(){
        this.dataForm = {}
      }

    }
  }
</script>
