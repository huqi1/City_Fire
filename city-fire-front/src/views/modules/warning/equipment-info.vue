<template>
  <el-dialog
    title="详情查看"
    width="900px"
    :close-on-click-modal="false"
    :visible.sync="visible">
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
   <!-- <el-form label-width="150px">
      <el-form-item label="备注信息：" style="width:750px">
        <el-input
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4}"
          placeholder="请输入内容"
          v-model="dataForm.remark" ></el-input>
      </el-form-item>
    </el-form>-->
    <span slot="footer" class="dialog-footer">
      <el-button @click="dataFormCancel()">取消</el-button>
      <el-button type="primary" @click="dataFormCancel()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { treeDataTranslate } from '@/utils'
  export default {
    data () {
      return {
        visible: false,
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
        }
      }
    },
    methods: {
      init (dataForm) {
        this.dataForm = dataForm
        this.visible = true
      },
      dataFormClear () {
        this.dataForm = {}
      },
      dataFormCancel () {
        this.dataFormClear()
        this.visible = false
      }
    }
  }
</script>

<style lang="scss">
</style>
