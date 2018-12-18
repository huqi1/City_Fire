<template>
  <el-dialog
    :title="saveflag ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="分类名称">
        <el-input v-model="dataForm.typeName" placeholder="分类名称 "></el-input>
      </el-form-item>
      <el-form-item label="上级分类">
        <el-popover
          ref="menuListPopover"
          placement="bottom-start"
          trigger="click">
          <el-tree
            :data="menuList"
            :props="menuListTreeProps"
            node-key="categoryId"
            ref="menuListTree"
            @current-change="menuListTreeCurrentChangeHandle"
            :default-expand-all="true"
            :highlight-current="true"
            :expand-on-click-node="false">
          </el-tree>
        </el-popover>
        <el-input v-model="dataForm.typePname" v-popover:menuListPopover :readonly="true" placeholder="点击选择上级菜单" class="menu-list__input"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dataFormCancel()">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { treeDataTranslate } from '@/utils'
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
        saveflag: true, // 判断是新增还是更新
        dataForm: {
          categoryId: '',
          typeName: '',
          typePid: '',
          typePname: '',
          typeId: ''
        },
        dataRule: {
          typeName: [
            { required: true, message: '分类名称不能为空', trigger: 'blur' }
          ],
          typePname: [
            { required: true, message: '上级分类不能为空', trigger: 'change' }
          ]
        },
        menuList: [],
        menuListTreeProps: {
          label: 'typeName',
          children: 'children'
        }
      }
    },
    methods: {
      init (id) {
        if(id != undefined && id !=''){
          this.saveflag = false //修改
          this.dataForm.categoryId = id
        }
        this.$http({
          url: this.$http.adornUrl('/category/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.menuList = treeDataTranslate(data.page.list, 'typeId' ,'typePid')
          console.log(this.menuList)
        }).then(() => {
          this.visible = true
          this.$nextTick(() => {
            this.$refs['dataForm'].resetFields()
          })
        }).then(() => {
          if (this.saveflag) {
          } else {
            // 修改
            this.$http({
              url: this.$http.adornUrl(`/category/info/${this.dataForm.categoryId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              this.dataForm.categoryId = data.cfCategory.categoryId
              this.dataForm.typeName = data.cfCategory.typeName
              this.dataForm.typePname = data.cfCategory.typePname
              this.dataForm.typePid = data.cfCategory.typePid
              this.dataForm.typeId = data.cfCategory.typeId
              console.log("当前操作的是:"+this.dataForm.typeId)
              this.menuListTreeSetCurrentNode()
            })
          }
        })
      },
      // 菜单树选中
      menuListTreeCurrentChangeHandle (data, node) {
        console.log(data)
        console.log("data.typeId:"+data.typeId)
        this.dataForm.typePid = data.typeId
        this.dataForm.typePname = data.typeName
       console.log("this.dataForm.typePid"+this.dataForm.typePid)
        console.log("this.dataForm.typePid"+data.typeName)
      },
      // 菜单树设置当前选中节点 主要用在修改
      menuListTreeSetCurrentNode () {
        this.$refs.menuListTree.setCurrentKey(this.dataForm.typePid)
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          console.log("this.dataForm："+this.dataForm.typeName)
          console.log("this.dataForm："+this.dataForm.typePid)
          console.log("this.dataForm："+this.dataForm.typePname)
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/category/${this.saveflag ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'categoryId': this.dataForm.categoryId,
                'typeId':  this.dataForm.typeId,
                'typeName': this.dataForm.typeName,
                'typePid': this.dataForm.typePid,
                'typePname': this.dataForm.typePname
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
        })
      },
      dataFormClear () {
        this.dataForm.typeName = ''
        this.dataForm.typePid = ''
        this.dataForm.typePname = ''
        this.dataForm.typeId = ''
      },
      dataFormCancel () {
        this.dataFormClear()
        this.saveflag = true
        this.visible = false
        this.$emit('refreshDataList')
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
