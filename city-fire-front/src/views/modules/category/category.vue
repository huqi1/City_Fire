<template>
  <div class="mod-menu">
    <el-form :inline="true" :model="dataForm">
      <el-form-item>
        <el-button v-if="isAuth('operate:category:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      style="width: 100%;">
<!--      <el-table-column
        prop="typeId"
        header-align="center"
        align="center"
        weight="10%"
        label="ID">
      </el-table-column>-->
      <table-tree-column
        prop="typeName"
        header-align="center"
        treeKey="typeId"
        parentKey="typePid"
        weight="30%"
        label="名称">
      </table-tree-column>
      <el-table-column
        prop="typePname"
        header-align="center"
        align="center"
        weight="30%"
        label="上级目录">
      </el-table-column>
     <!-- <el-table-column
        prop="typeLevel"
        weight="8%"
        header-align="center"
        align="center"
        label="等级">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.typeLevel == 0" size="medium">一级</el-tag>
          <el-tag v-else-if="scope.row.typeLevel == 1" size="small" type="success">二级</el-tag>
          <el-tag v-else-if="scope.row.typeLevel == 2" size="small" type="warning">三级</el-tag>
          <el-tag v-else-if="scope.row.typeLevel == 3" size="small" type="danger">四级</el-tag>
          <el-tag v-else="" size="danger" type="danger">五级</el-tag>
        </template>
      </el-table-column>-->
      <el-table-column
        prop="gmtCreate"
        header-align="center"
        align="center"
        weight="12%"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        weight="10%"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('operate:category:update')" type="primary" size="medium" @click="addOrUpdateHandle(scope.row.categoryId)">修改</el-button>
          <el-button v-if="isAuth('operate:category:delete')" type="danger" size="medium" @click="deleteHandle(scope.row.categoryId,scope.row.typeName)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import TableTreeColumn from '@/components/table-tree-column'
  import AddOrUpdate from './category-add-or-update'
  import { treeDataTranslate } from '@/utils'
  export default {
    data () {
      return {
        dataForm: {},
        dataList: [],
        dataListLoading: false,
        addOrUpdateVisible: false
      }
    },
    components: {
      TableTreeColumn,
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/category/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataList = treeDataTranslate(data.page.list, 'typeId', 'typePid')
          this.dataListLoading = false
        })
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id, name) {
        this.$confirm(`确定对名称为【${name}】进行【删除】操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/category/delete`),
            method: 'post',
            params: this.$http.adornParams({
              categoryId: id
            })

          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {})
      }
    }
  }
</script>
