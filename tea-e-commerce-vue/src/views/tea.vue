<script setup>

import { ref } from 'vue'
import {teaListService, teaDeleteServise, teaAddService , teaEditService} from '@/api/tea.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

import {
    Edit,
    Delete,
    Plus
} from '@element-plus/icons-vue'

const adminData = localStorage.getItem('adminToken')
const parsedData = JSON.parse(adminData); // 解析为对象
const imgUrl= ref()

//上传图片成功回调
const uploadSuccess = (result) => {
    imgUrl.value = result.data
}
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
    teas.value = result.data.rows
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
//控制抽屉是否显示
const visibleDrawer = ref(false)
// 添加茶叶表单数据模型
const teaModel = ref({
    id: '',
    name: '',
    category: '',
    origin: '',
    price: '',
    description: '',
    image: ''
})
//添加茶叶
const addTea = async () => {
    // 去除HTML标签
    teaModel.value.description = teaModel.value.description.replace(/<[^>]+>/g, '')
    teaModel.value.image = imgUrl.value
    console.log(teaModel.value)
    let result = await teaAddService(teaModel.value)
    ElMessage.success('添加成功')
    visibleDrawer.value = false
    // 清空imgUrl的值
    imgUrl.value = '' 
    teaList()
}
//定义变量控制标题显示
const title = ref('')
//编辑茶叶
const showEditDrawer = async (row) => {
    visibleDrawer.value = true
    title.value = '编辑茶叶'
    teaModel.value.name = row.name
    teaModel.value.category = row.category
    teaModel.value.origin = row.origin
    teaModel.value.price = row.price
    teaModel.value.description = row.description
    teaModel.value.expirationDate = row.expirationDate
    teaModel.value.image = row.image
    teaModel.value.id = row.id
}
//清空茶叶表单数据
const clearTeaModel = () => {
    teaModel.value = {
        id: '',
        name: '',
        category: '',
        origin: '',
        price: '',
        description: '<p></p>',
        image: ''
    }
}
//编辑茶叶
const editTea = async () => {
    // 去除HTML标签
    teaModel.value.description = teaModel.value.description.replace(/<[^>]+>/g, '')
    let result = await teaEditService(teaModel.value)
    ElMessage.success('编辑成功')
    visibleDrawer.value = false
    teaList()
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>茶叶信息管理</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true;title='上架新品';clearTeaModel()">上架新品</el-button>
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
                <el-button type="primary" @click="teaList();ElMessage.success('更新成功')">搜索</el-button>
                <el-button @click="categoryName='',teaList()">重置</el-button>
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
                    <el-button :icon="Edit" circle plain type="primary" @click="showEditDrawer(row)"></el-button>
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
        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="50%">
            <!-- 添加文章表单 -->
            <el-form :model="teaModel" label-width="100px" >
                <el-form-item label="茶叶名称" >
                    <el-input v-model="teaModel.name" placeholder="请输入茶叶名称"></el-input>
                </el-form-item>
                <el-form-item label="茶叶分类">
                    <el-select placeholder="请选择" v-model="teaModel.category">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.categoryName">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="茶叶原产地" >
                    <el-input v-model="teaModel.origin" placeholder="请输入茶叶原产地"></el-input>
                </el-form-item>
                <el-form-item label="茶叶价格" >
                    <el-input v-model="teaModel.price" placeholder="请输入茶叶价格"></el-input>
                </el-form-item>
                <el-form-item label="茶叶封面">
                    <el-upload 
                    ref="uploadRef"
                    class="avatar-uploader" 
                    :auto-upload="true" 
                    :show-file-list="false"
                    action="/api/upload" 
                    name="file" 
                    :headers="{'token':parsedData.token}"
                    :on-success="uploadSuccess"
                    >
                        <!-- 修改判断条件 -->
                        <img v-if="imgUrl!== ''" :src="imgUrl" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="茶叶详情描述">
                    <div class="editor">
                        <quill-editor theme="snow" v-model:content="teaModel.description" contentType="html"></quill-editor>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="title==='上架新品'?addTea():editTea()">发布</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
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
        span {
            font-size: 20px;
            font-weight: bold;
            color: #333;
            }
    }
}
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>


