<script setup>
import {
    Check,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getCommentListService } from '@/api/comment'
const comments = ref()

//滚动分页
const getCommentList = async () => {
    let result = await getCommentListService()
    comments.value = result.data
}
getCommentList()
</script>

<template>
  <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>评论管理</span>
            </div>
        </template>
        <el-table :data="comments" class="table" height="482">
            <!-- 添加 align="center" 让文字居中 -->
            <el-table-column label="序号" width="100" type="index" align="center"> </el-table-column>
            <el-table-column label="评论用户" prop="customerName" align="center"></el-table-column>
            <el-table-column label="评论商品" prop="teaName" align="center"></el-table-column>
            <el-table-column label="评分" width="100" prop="grade" align="center"></el-table-column>
            <el-table-column label="评论内容" width="450" prop="content" align="center"></el-table-column>
            <el-table-column label="评论状态" prop="state" align="center">
                <template #default="{ row }">
                    <span v-if="row.state === '已通过'" style="color: green;">已通过</span>
                    <span v-else-if="row.state === '待审核'" style="color: gray;">待审核</span>
                    <span v-else-if="row.state === '已驳回'" style="color: red;">已驳回</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template #default="{ }">
                  <el-button :icon="Check" circle type="success"></el-button>
                  <el-button :icon="Delete" circle type="danger"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
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
    .table {
        width: 100%;
    }
}
</style>