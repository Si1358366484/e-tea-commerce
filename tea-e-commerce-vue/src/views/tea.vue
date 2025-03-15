<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'
import {teaListService, teaDeleteServise} from '@/api/tea.js'
import { ElMessage, ElMessageBox } from 'element-plus'
//分类数据模型
const categorys = ref([
    {
        "id": 1,
        "categoryName": "绿茶",
    },
    {
        "id": 2,
        "categoryName": "红茶",
    },
    {
        "id": 3,
        "categoryName": "白茶",
    },
    {
        "id": 4,
        "categoryName": "普洱茶",
    }
])

const categoryName=ref('')

const teas = ref()

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    teaList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    teaList()
}
//查询茶叶所有信息
const teaList = async () => {
    let params = {
        pageNum:pageNum.value,
        pageSize:pageSize.value,
        categoryName:categoryName.value?categoryName.value:null,
    };
    let result = await teaListService(params)
    total.value = result.data.total
    teas.value = result.data.items
}
teaList()
//删除茶叶
const deleteTea = (id) => {
    ElMessageBox.confirm(
    '你确定要删除吗',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {
        let result = await teaDeleteServise(id)
        console.log(result)
        //再次获取文章分类列表
        teaList()
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '用户取消了删除',
      })
    })
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>茶叶信息管理</span>
                <div class="extra">
                    <el-button type="primary">上架新品</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="茶叶分类：">
                <el-select placeholder="请选择" v-model="categoryName" style="width: 200px;">
                    <el-option 
                        v-for="c in categorys" 
                        :key="c.id" 
                        :label="c.categoryName"
                        :value="c.categoryName">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="teaList">搜索</el-button>
                <el-button @click="categoryName=''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 文章列表 -->
        <el-table :data="teas" style="width: 100%">
            <el-table-column label="茶叶名称" width="400" prop="name"></el-table-column>
            <el-table-column label="分类" prop="category"></el-table-column>
            <el-table-column label="原产地" prop="origin"> </el-table-column>
            <el-table-column label="价格" prop="price"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteTea(row.id)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5 ,10,]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>


