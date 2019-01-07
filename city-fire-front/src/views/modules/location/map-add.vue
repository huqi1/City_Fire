<template>
  <el-dialog
    title="新增地区信息"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :inline="true" :model="dataform"  ref="dataform" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="省：">
        <el-input v-model="dataform.province" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="市：">
        <el-input v-model="dataform.city" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="市级编码：">
        <el-input v-model="dataform.citycode" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="区：">
      <el-input v-model="dataform.district" readonly="readonly"></el-input>
    </el-form-item>
      <el-form-item label="区级编码：">
        <el-input v-model="dataform.adcode" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="街道：">
        <el-input v-model="dataform.street" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="街道编号：">
        <el-input v-model="dataform.streetNumber" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="经度：">
        <el-input v-model="dataform.businessAreas[0].location.lat" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="纬度：">
        <el-input v-model="dataform.businessAreas[0].location.lng" readonly="readonly"></el-input>
      </el-form-item>
    </el-form>
    <el-form :model="dataform"  ref="dataform"  label-width="100px">
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
        dataform: {
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
      init (dataform,choseLocation) {
        this.dataform = dataform
        this.choesLocation = choseLocation
        this.visible = true
      },
      // 表单提交
      dataFormSubmit () {
        let self = this
          this.$http({
            url: this.$http.adornUrl(`/location/save`),
            method: 'post',
            data: this.$http.adornData({
              'locationName': this.choesLocation,
              'province':  this.dataform.province,
              'city': this.dataform.city,
              'citycode': this.dataform.citycode,
              'district': this.dataform.district,
              'adcode': this.dataform.adcode,
              'street': this.dataform.street,
              'streetNumber': this.dataform.streetNumber,
              'lat': this.dataform.businessAreas[0].location.lat,
              'lng': this.dataform.businessAreas[0].location.lng,
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  self.dataFormCancel()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        },
      dataFormCancel () {
        this.visible = false
        //this.dataform = {}              //给obj赋值空
      }
      },


  }
</script>

<style lang="scss">
</style>
