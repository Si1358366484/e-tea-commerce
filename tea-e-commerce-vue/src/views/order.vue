<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getOrderListService,updateStateService,searchOrderService } from '@/api/order'
const orders = ref()
const orderReference = ref()
const getOrderList = async () => {
    let result = await getOrderListService()
    orders.value = result.data.reverse()
}
getOrderList()

const updateState = async (state,id) => {
    let result = await updateStateService(state,id)
    ElMessage.success('更新成功')
    getOrderList()
}
const search = async (orderReference) => {
    let result = await searchOrderService(orderReference)
    orders.value = [result.data]
    ElMessage.success('搜索成功')
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>订单管理</span>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="订单编号:">
              <el-input v-model="orderReference" placeholder="请输入订单编号" style="width: 200px;"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="search(orderReference)">搜索</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="orders" class="table" height="482">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="订单编号" prop="orderReference"></el-table-column>
            <el-table-column label="订单总价" prop="orderAmounts"></el-table-column>
            <el-table-column label="商品名称" prop="teaName"></el-table-column>
            <el-table-column label="商品数量" prop="teaNumber"></el-table-column>
            <el-table-column label="商品状态" prop="state">
                <template #default="{ row }">
                    <span v-if="row.state === '已发货'" style="color: green;">已发货</span>
                    <span v-else-if="row.state === '已付款'" style="color: orange;">已付款</span>
                    <span v-else-if="row.state === '已退货'" style="color: red;">已退货</span>
                </template>
            </el-table-column>
            <el-table-column label="收货人" prop="customerName"></el-table-column>
            <el-table-column label="操作">
                <template #default="{ row }">
                  <el-button type="success" @click="updateState('已发货',row.id)">发货</el-button>
                  <el-button type="danger" @click="updateState('已退货',row.id)">退货</el-button>
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