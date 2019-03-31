<template>
  <el-dialog
    title="状态变更"
    width="900px"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :inline="true" :model="dataForm"  ref="dataForm" label-width="150px">
      <el-form-item label="设备名称：">
        <el-input v-model="dataForm.equipmentName" readonly="readonly" ></el-input>
      </el-form-item>
    </el-form>
    <el-form :inline="true"  ref="dataForm" :model="dataForm" label-width="150px">
      <el-form-item label="设备位置：">
        <el-input v-model="dataForm.locationName"
                   readonly="readonly"
                   style="width:535px">
        </el-input>
      </el-form-item>
    </el-form>
    <el-form label-width="150px"  ref="dataForm" :model="dataForm">
      <el-form-item label="设备编码：">
        <el-input v-model="dataForm.equipmentId" readonly="readonly" style="width:535px"></el-input>
      </el-form-item>
    </el-form>
    <el-form label-width="150px" :rules="dataRules"  ref="dataForm" :model="dataForm">
      <el-form-item label="修改设备状态：" prop="operatorStatus">
        <el-select v-model="dataForm.operatorStatus"  readonly="readonly" style="width:535px">
          <el-option
            v-for="item in equipmentStatusList"
            :key="item.id"
            :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <el-form label-width="150px" :rules="dataRules"  ref="dataForm" :model="dataForm">
      <el-form-item label="操作人：" prop="warningOperator">
        <el-input v-model="dataForm.warningOperator" style="width:535px"></el-input>
      </el-form-item>
    </el-form>
    <el-form label-width="150px" :rules="dataRules"  ref="dataForm" :model="dataForm">
      <el-form-item label="修改原因：" prop="warningReason">
        <el-input
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 5}"
          placeholder="请输入内容"
          v-model="dataForm.warningReason" style="width:535px" ></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dataFormCancel()">取消</el-button>
      <el-button type="primary" @click="insertData()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        choesLocation: '',
        oldequipmentId:'',
        dataForm: {
          equipmentId:'',
          equipmentName:'',
          warningOperator:'', //报修人
          warningReason:'', //报修原因
          operatorStatus:'',//操作项
          locationName:''
        },
        equipmentStatusList:[
          {
            id: '0',
            name: '正常'
          },
          {
            id: '1',
            name: '报修'
          },
          {
            id: '2',
            name: '报废'
          }
        ],
        dataRules:{
          warningOperator :[
            {required: true, message: '操作人不能为空', trigger: 'blur' }
          ],
          warningReason :[
            {required: true, message: '操作原因不能为空', trigger: 'blur' }
          ],
          operatorStatus :[
            {required: true, message: '操作项不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (dataForm) {
        this.dataForm.equipmentId = dataForm.equipmentId
        this.dataForm.equipmentName = dataForm.equipmentName
        this.dataForm.locationName = dataForm.locationName
        this.visible = true
      },
      dataFormCancel () {
        this.dataForm = {}
        this.visible = false
      },
      insertData(){
        let self = this
        var OperatorStatus = 0;
        if(self.dataForm.operatorStatus == '报修'){
          OperatorStatus = 1;
        }else if (self.dataForm.operatorStatus == '报废'){
          OperatorStatus = 2;
        }

        this.$http({
          url:this.$http.adornUrl(`/report/save`),
          method: 'post',
          data:this.$http.adornData({
            equipmentId:self.dataForm.equipmentId,
            equipmentName:self.dataForm.equipmentName,
            warningOperator:self.dataForm.warningOperator,
            operatorStatus:OperatorStatus,
            warningReason:self.dataForm.warningReason
          })
        }).then( ({data}) => {
          if (data && data.code === 0) {
            self.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                self.visible = false
                self.dataFormCancel()
                self.$emit('refreshDataList')
              }
            })
          } else {
            this.$message.error(data.msg)
          }

        })
      }
    }
  }
</script>

<style lang="scss">
</style>
