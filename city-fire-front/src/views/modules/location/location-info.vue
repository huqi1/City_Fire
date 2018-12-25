<template>
  <el-dialog
    title="详情查看"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :inline="true" label-width="100px">
      <el-form-item label="省：">
        <el-input v-model="dataForm.province" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="市：">
        <el-input v-model="dataForm.city" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="市级编码：">
        <el-input v-model="dataForm.citycode" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="区：">
      <el-input v-model="dataForm.district" readonly="readonly"></el-input>
    </el-form-item>
      <el-form-item label="区级编码：">
        <el-input v-model="dataForm.adcode" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="街道：">
        <el-input v-model="dataForm.street" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="街道编号：">
        <el-input v-model="dataForm.streetNumber" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="经度：">
        <el-input v-model="dataForm.lat" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="纬度：">
        <el-input v-model="dataForm.lng" readonly="readonly"></el-input>
      </el-form-item>
    </el-form>
    <el-form label-width="100px">
      <el-form-item label="详细信息：">
        <el-input v-model="dataForm.locationName" readonly="readonly"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dataFormCancel()">取消</el-button>
      <el-button type="primary" @click="dataFormCancel()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        choesLocation: '',
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
        }
      }
    },
    methods: {
      init (dataForm) {
        this.dataForm = dataForm
        this.visible = true
      },
      dataFormClear () {
        this.dataForm = ''
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
