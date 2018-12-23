<template>
  <el-dialog
    title="新增地区信息"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :inline="true" :model="dataForm"  ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
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
        <el-input v-model="dataForm.businessAreas[0].location.lat" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="纬度：">
        <el-input v-model="dataForm.businessAreas[0].location.lng" readonly="readonly"></el-input>
      </el-form-item>
    </el-form>
    <el-form :model="dataForm"  ref="dataForm"  label-width="100px">
      <el-form-item label="详细信息：">
        <el-input v-model="choesLocation" readonly="readonly"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dataFormCancel()">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
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
          citycode: '',
          adcode: '',
          businessAreas: [{
            name: '',
            id: '',
            location: {
              P: '',
              O: '',
              lng: '000',
              lat: '000'
            }
          }],
          neighborhoodType: '',
          neighborhood: '',
          building: '',
          buildingType: '',
          township: '',
          province: '',
          city: '',
          district: '',
          street: '',
          streetNumber: ''
        }
      }
    },
    methods: {
      init (dataForm,choseLocation) {
        this.dataForm = dataForm
        this.choesLocation = choseLocation
        this.visible = true
      },
      dataFormClear () {
        this.dataForm = ''
      },
      // 表单提交
      dataFormSubmit () {
          this.$http({
            url: this.$http.adornUrl(`/location/save`),
            method: 'post',
            data: this.$http.adornData({
              'locationName': this.choesLocation,
              'province':  this.dataForm.province,
              'city': this.dataForm.city,
              'citycode': this.dataForm.citycode,
              'district': this.dataForm.district,
              'adcode': this.dataForm.adcode,
              'street': this.dataForm.street,
              'streetNumber': this.dataForm.streetNumber,
              'lat': this.dataForm.businessAreas[0].location.lat,
              'lng': this.dataForm.businessAreas[0].location.lng,
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.dataFormCancel()
                  this.saveflag = true
                  this.$emit('refreshDataList')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      },
      dataFormCancel () {
        this.dataFormClear()
        this.visible = false
      }

  }
</script>

<style lang="scss">
</style>
