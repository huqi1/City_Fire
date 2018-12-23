<template>
  <el-dialog
    title="新增地区信息"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="省：">
        <el-input v-model="dataForm.province" placeholder="省 "></el-input>
      </el-form-item>
      <el-form-item label="市：">
        <el-input v-model="dataForm.city" placeholder="市 "></el-input>
      </el-form-item>
      <el-form-item label="区：">
        <el-input v-model="dataForm.district" placeholder="区 "></el-input>
      </el-form-item>
      <el-form-item label="街道：">
        <el-input v-model="dataForm.street" placeholder="街道 "></el-input>
      </el-form-item>
      <el-form-item label="详细信息：">
        <el-input v-model="choesLocation" placeholder="详细信息"></el-input>
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
      var validateUrl = (rule, value, callback) => {
        if (this.dataForm.typeName === '' && !/\S/.test(value)) {
          callback(new Error('分类名称不能为空'))
        } else {
          callback()
        }
      }
      return {
        visible: false,
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
        },
        dataRule: {
          typeName: [
            { required: true, message: '分类名称不能为空', trigger: 'blur' }
          ],
          typePname: [
            { required: true, message: '上级分类不能为空', trigger: 'change' }
          ]
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
        this.dataForm.typeName = ''
        this.dataForm.typePid = ''
        this.dataForm.typePname = ''
        this.dataForm.typeId = ''
      },
      dataFormCancel () {
        this.dataFormClear()
        this.visible = false
      }
    }
  }
</script>

<style lang="scss">
  .mod-menu {
    .menu-list__input,
    .icon-list__input {
      > .el-input__inner {
        cursor: pointer;
      }
    }
    &__icon-popover {
      max-width: 370px;
    }
    &__icon-list {
      max-height: 180px;
      padding: 0;
      margin: -8px 0 0 -8px;
      > .el-button {
        padding: 8px;
        margin: 8px 0 0 8px;
        > span {
          display: inline-block;
          vertical-align: middle;
          width: 18px;
          height: 18px;
          font-size: 18px;
        }
      }
    }
    .icon-list__tips {
      font-size: 18px;
      text-align: center;
      color: #e6a23c;
      cursor: pointer;
    }
  }
</style>
