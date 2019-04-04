<template>
  <el-dialog
    title="报警详情查看"
    width="900px"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :inline="true" label-width="150px"  ref="WarningFrom" :model="WarningFrom">
      <el-form-item label="操作人：" >
        <el-input v-model="WarningFrom.warningOperator" readonly="readonly" ></el-input>
      </el-form-item>
      <el-form-item label="时间：">
        <el-input v-model="WarningFrom.warningTime" readonly="readonly" ></el-input>
      </el-form-item>
      <el-form-item label="报警项：">
        <el-input v-if="WarningFrom.operatorStatus == 0" value="正常" readonly="readonly" ></el-input>
        <el-input v-if="WarningFrom.operatorStatus == 1" value="报修" readonly="readonly" ></el-input>
        <el-input v-if="WarningFrom.operatorStatus == 2" value="报废" readonly="readonly" ></el-input>
      </el-form-item>
    </el-form>
    <el-form label-width="150px"  ref="WarningFrom" :model="WarningFrom">
      <el-form-item label="报警原因：" prop="warningReason">
        <el-input
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 3}"
          v-model="WarningFrom.warningReason"readonly="readonly"  style="width:535px" ></el-input>
      </el-form-item>
    </el-form>
    <div class="demonstration">
      <el-slider range
                 :step="10"
                 show-stops v-model="slidervalue"></el-slider>
    </div>
    <div style="text-align: left; margin-bottom: 20px">
      <font size="4" color="#17b3a3">设备报警处理</font>
    </div>
    <el-form label-width="150px" :rules="dataRules"  ref="WarningFrom" :model="WarningFrom">
      <el-form-item label="处理方案：" prop="dealPlan">
        <el-input
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 4}"
          placeholder="请输入内容"
          v-model="WarningFrom.dealPlan" style="width:535px" ></el-input>
      </el-form-item>
    </el-form>
    <el-form :inline="true" label-width="150px" :rules="dataRules"  ref="WarningFrom" :model="WarningFrom">
      <el-form-item label="处理结果：" prop="status">
        <el-select v-model="WarningFrom.status"  readonly="readonly" style="width:150px">
          <el-option
            v-for="item in dealStatusList"
            :value="item.key"
            :label="item.value"
            :key="item.key">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="处理人：" prop="dealOperator">
        <el-input v-model="WarningFrom.dealOperator" style="width:150px"></el-input>
      </el-form-item>
      <el-form-item v-if="isDeal == true" label="处理时间：" prop="dealTime">
        <el-input v-model="WarningFrom.dealTime" style="width:150px"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: right">
      <span v-if="isDeal == false" style="margin-right: 100px" >
        <el-button @click="dataFormCancel()">取消</el-button>
        <el-button type="primary" @click="updateWarning()">确定</el-button>
      </span>
    </div>
    <div class="demonstration">
      <el-slider range
                 :step="10"
                 show-stops v-model="slidervalue"></el-slider>
    </div>
    <div style="text-align: left">
      <font size="4" color="#17b3a3">设备详细信息</font>
    </div>
    <el-form :inline="true" :model="dataForm"  ref="dataForm"  label-width="150px">
      <el-form-item label="设备名称：" >
        <el-input v-model="dataForm.equipmentName" readonly="readonly" placeholder="输入设备名称" ></el-input>
      </el-form-item>
      <el-form-item label="选择分类：" >
        <el-input v-model="dataForm.belongTypename"  :readonly="true" class="menu-list__input">
        </el-input>
      </el-form-item>
      <el-form-item label="数量(件)：" >
        <el-input v-model="dataForm.equipmentNum" readonly="readonly"></el-input>
      </el-form-item>
    </el-form>
    <el-form :inline="true"  ref="dataForm" :model="dataForm" label-width="150px">
      <el-form-item label="设备位置：" >
        <el-input v-model="dataForm.locationName" readonly="readonly" style="width:535px"></el-input>
      </el-form-item>
    </el-form>
    <el-form label-width="150px" ref="dataForm" :model="dataForm">
      <el-form-item label="设备编码：">
        <el-input v-model="dataForm.equipmentId" readonly="readonly" style="width:535px"></el-input>
      </el-form-item>
    </el-form>
    <el-form :inline="true" label-width="150px"  ref="dataForm" :model="dataForm">
      <el-form-item label="小区：">
        <el-input v-model="dataForm.community" readonly="readonly" ></el-input>
      </el-form-item>
      <el-form-item label="单元：">
        <el-input v-model="dataForm.unit" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="楼层：">
        <el-input v-model="dataForm.floor" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="楼道：">
        <el-input v-model="dataForm.corridor" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="房间号">
        <el-input v-model="dataForm.roomNumber" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="管理员：" >
        <el-input v-model="dataForm.administrator" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="联系电话：">
        <el-input v-model="dataForm.phone" readonly="readonly"></el-input>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        isDeal:false,
        slidervalue:[50,50],
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
        dealStatusList:[{
          key:0,
          value:"已解决"
        },
          {
            key:1,
            value:"未解决"
          }],
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
        dataRules:{
          dealPlan :[
            {required: true, message: '处理方案不能为空', trigger: 'blur' }
          ],
          dealOperator :[
            {required: true, message: '处理人不能为空', trigger: 'change' }
          ]
        }
      }
    },
    methods: {
      init (dataForm,equipmentId,isDeal) {
        this.WarningFrom = dataForm
        this.isDeal = isDeal
        this.$http({
          url: this.$http.adornUrl(`/equipment/info/${equipmentId}`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataForm = data.cfEquipment
          this.visible = true
        })
      },
      dataFormClear () {
        this.dataForm = {}
      },
      dataFormCancel () {
        this.dataFormClear()
        this.visible = false
      },
      updateWarning(){
        this.$refs['WarningFrom'].validate((valid)=>{
          if (valid) {
              this.$http({
                url: this.$http.adornUrl('/report/dealwarning'),
                method:'post',
                data: this.$http.adornData({
                  recordId:this.WarningFrom.recordId,
                  dealOperator:this.WarningFrom.dealOperator,
                  dealPlan:this.WarningFrom.dealPlan,
                  status:this.WarningFrom.status
                })
              }).then(({data})=>{
                if (data && data.code === 0) {
                  this.$message({
                    message: '操作成功',
                    type: 'success',
                    duration: 1500,
                    onClose: () => {
                      this.dataFormCancel()
                      this.$emit('refreshDataList')
                    }
                  })
                } else {
                  this.$message.error(data.msg)
                }
              })
          }
        })
      }
    }
  }
</script>

<style lang="scss">
</style>
